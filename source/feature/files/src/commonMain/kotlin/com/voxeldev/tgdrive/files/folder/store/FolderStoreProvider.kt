package com.voxeldev.tgdrive.files.folder.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.voxeldev.tgdrive.favorites.api.FavoriteMessage
import com.voxeldev.tgdrive.files.api.file.DeleteFilesRequest
import com.voxeldev.tgdrive.files.api.file.LocalFile
import com.voxeldev.tgdrive.files.api.file.MessageFile
import com.voxeldev.tgdrive.files.api.file.SendFileRequest
import com.voxeldev.tgdrive.files.api.file.TGDriveFile
import com.voxeldev.tgdrive.files.api.platform.FileViewer
import com.voxeldev.tgdrive.files.folder.integration.AddFavoriteUseCase
import com.voxeldev.tgdrive.files.folder.integration.DeleteFilesFromChatUseCase
import com.voxeldev.tgdrive.files.folder.integration.DownloadFileUseCase
import com.voxeldev.tgdrive.files.folder.integration.GetAllFavoritesUseCase
import com.voxeldev.tgdrive.files.folder.integration.GetChatDocumentsUseCase
import com.voxeldev.tgdrive.files.folder.integration.GetLocalFileUseCase
import com.voxeldev.tgdrive.files.folder.integration.RemoveSingleFavoriteUseCase
import com.voxeldev.tgdrive.files.folder.integration.SendFileUseCase
import com.voxeldev.tgdrive.files.folder.integration.UploadFileUseCase
import com.voxeldev.tgdrive.files.folder.store.FolderStore.Intent
import com.voxeldev.tgdrive.files.folder.store.FolderStore.State
import com.voxeldev.tgdrive.main.api.files.File
import com.voxeldev.tgdrive.utils.extensions.getMessage
import com.voxeldev.tgdrive.utils.extensions.indexOrNull
import com.voxeldev.tgdrive.utils.integration.BaseUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * @author nvoxel
 */
internal class FolderStoreProvider(
    private val folderId: Long,
    private val storeFactory: StoreFactory,
    private val fileFlow: Flow<File>,
    private val fileViewer: FileViewer?,
    private val getChatDocumentsUseCase: GetChatDocumentsUseCase = GetChatDocumentsUseCase(),
    private val downloadFileUseCase: DownloadFileUseCase = DownloadFileUseCase(),
    private val getLocalFileUseCase: GetLocalFileUseCase = GetLocalFileUseCase(),
    private val uploadFileUseCase: UploadFileUseCase = UploadFileUseCase(),
    private val sendFileUseCase: SendFileUseCase = SendFileUseCase(),
    private val deleteFilesFromChatUseCase: DeleteFilesFromChatUseCase = DeleteFilesFromChatUseCase(),
    private val getAllFavoritesUseCase: GetAllFavoritesUseCase = GetAllFavoritesUseCase(),
    private val addFavoriteUseCase: AddFavoriteUseCase = AddFavoriteUseCase(),
    private val removeSingleFavoriteUseCase: RemoveSingleFavoriteUseCase = RemoveSingleFavoriteUseCase(),
) {

    fun provide(): FolderStore =
        object :
            FolderStore,
            Store<Intent, State, Nothing> by storeFactory.create(
                name = STORE_NAME,
                initialState = State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl,
            ) {}

    private sealed interface Msg {
        data object FilesListLoading : Msg
        data class FilesListLoaded(val files: MutableList<TGDriveFile>) : Msg

        data class FileDownloading(val fileId: Int) : Msg
        data class FileDownloadProgressChanged(val fileId: Int, val progress: Float) : Msg
        data class FileDownloaded(val file: File) : Msg

        data object UpdateBottomSheetToggled : Msg
        data class FileUploading(val fileId: Int, val localFile: LocalFile) : Msg
        data class FileUploadProgressChanged(val fileId: Int, val progress: Float) : Msg
        data class FileUploaded(val fileId: Int) : Msg

        data class FileMenuVisibilityChanged(val isVisible: Boolean) : Msg

        data class DeleteFileDialogVisibilityChanged(val isVisible: Boolean) : Msg

        data class FileDeleted(val messageId: Long) : Msg

        data class FileChanged(val index: Int, val file: TGDriveFile) : Msg

        data class PendingFileChanged(val messageId: Long?) : Msg

        data class Error(val message: String) : Msg
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Unit, State, Msg, Nothing>() {

        override fun executeAction(action: Unit, getState: () -> State) {
            loadFiles()

            scope.launch {
                fileFlow.collect { file ->
                    val state = getState()
                    if (state.downloadingFiles.containsKey(file.id)) {
                        if (file.isDownloaded()) dispatch(message = Msg.FileDownloaded(file = file))
                        else dispatch(
                            message = Msg.FileDownloadProgressChanged(
                                fileId = file.id,
                                progress = calculateFileDownloadProgress(file = file),
                            )
                        )
                    } else if (state.uploadingFiles.find { it.id == file.id } != null) {
                        if (!file.remote.isUploadingActive && file.remote.uploadedSize == file.expectedSize) {
                            dispatch(message = Msg.FileUploaded(fileId = file.id))
                            sendUploadedFile(chatId = folderId, file = file)
                        } else dispatch(
                            message = Msg.FileUploadProgressChanged(
                                fileId = file.id,
                                progress = calculateFileUploadProgress(file = file),
                            )
                        )
                    }
                }
            }
        }

        override fun executeIntent(intent: Intent, getState: () -> State) {
            val state = getState()

            when (intent) {
                is Intent.FileClicked -> {
                    val index = state.files.indexOrNull { it.messageFile.file.id == intent.id } ?: return
                    val file = state.files[index]

                    if (file.messageFile.file.isDownloaded()) {
                        fileViewer?.openFile(file = file.messageFile.file)
                    } else {
                        downloadFile(fileId = intent.id)
                    }
                }

                is Intent.FileFavoriteClicked -> {
                    val index = state.files.indexOrNull { it.messageFile.messageId == intent.messageId } ?: return
                    val file = state.files[index]

                    if (file.favoriteMessage == null) addToFavorites(index = index, file = file)
                    else removeFromFavorites(index = index, file = file)
                }

                is Intent.Reload -> loadFiles()

                is Intent.UploadsBottomSheetToggle -> dispatch(message = Msg.UpdateBottomSheetToggled)
                is Intent.UploadFile -> getLocalFile(filePointer = intent.filePointer)

                is Intent.OpenFileMenu -> {
                    dispatch(message = Msg.FileMenuVisibilityChanged(isVisible = true))
                    dispatch(message = Msg.PendingFileChanged(messageId = intent.messageId))
                }

                is Intent.DismissFileMenu -> {
                    dispatch(message = Msg.FileMenuVisibilityChanged(isVisible = false))
                    // TODO: SwiftUI confirmationDialog sends dismiss after its item was clicked
                    //dispatch(message = Msg.PendingFileChanged(messageId = null))
                }

                is Intent.DeleteFile -> {
                    dispatch(message = Msg.FileMenuVisibilityChanged(isVisible = false))
                    dispatch(message = Msg.DeleteFileDialogVisibilityChanged(isVisible = true))
                }

                is Intent.ConfirmDeleteFile -> {
                    val index = state.files.indexOrNull { it.messageFile.messageId == state.pendingFileMessageId } ?: return
                    val file = state.files[index]

                    deleteFile(index = index, file = file)
                }
                is Intent.DismissDeleteFile -> {
                    dispatch(message = Msg.DeleteFileDialogVisibilityChanged(isVisible = false))
                    dispatch(message = Msg.PendingFileChanged(messageId = null))
                }
            }
        }

        private fun loadFiles() {
            dispatch(message = Msg.FilesListLoading)
            getFiles()
        }

        private fun getFiles() {
            getChatDocumentsUseCase(params = folderId, scope = scope) { result ->
                result.fold(
                    onSuccess = { files -> getFavorites(messageFiles = files) },
                    onFailure = { error(message = it.getMessage()) },
                )
            }
        }

        private fun getFavorites(messageFiles: List<MessageFile>) {
            getAllFavoritesUseCase(params = BaseUseCase.NoParams, scope = scope) { result ->
                result.fold(
                    onSuccess = { favorites ->
                        favorites.collect { favoritesList ->
                            val files = messageFiles.map { messageFile ->
                                TGDriveFile(
                                    messageFile = messageFile,
                                    favoriteMessage = favoritesList.find { it.chatId == folderId && it.messageId == messageFile.messageId },
                                )
                            }

                            dispatch(message = Msg.FilesListLoaded(files = files.toMutableList()))
                        }
                    },
                    onFailure = { error(message = it.getMessage()) }
                )
            }
        }

        private fun downloadFile(fileId: Int) {
            downloadFileUseCase(params = fileId, scope = scope) { result ->
                result.fold(
                    onSuccess = { file -> dispatch(message = Msg.FileDownloading(fileId = file.id)) },
                    onFailure = { error(message = it.getMessage()) },
                )
            }
        }

        private fun getLocalFile(filePointer: String) {
            getLocalFileUseCase(params = filePointer, scope = scope) { result ->
                result.fold(
                    onSuccess = { localFile -> uploadFile(localFile = localFile) },
                    onFailure = { error(message = it.getMessage()) },
                )
            }
        }

        private fun uploadFile(localFile: LocalFile) {
            uploadFileUseCase(params = localFile.path, scope = scope) { result ->
                result.fold(
                    onSuccess = { file -> dispatch(message = Msg.FileUploading(fileId = file.id, localFile = localFile)) },
                    onFailure = { error(message = it.getMessage()) },
                )
            }
        }

        private fun sendUploadedFile(chatId: Long, file: File) {
            sendFileUseCase(
                params = SendFileRequest(chatId = chatId, file = file),
                scope = scope,
            ) { result ->
                result.fold(
                    onSuccess = { loadFiles() },
                    onFailure = { error(message = it.getMessage()) },
                )
            }
        }

        private fun deleteFile(index: Int, file: TGDriveFile) {
            dispatch(message = Msg.DeleteFileDialogVisibilityChanged(isVisible = false))

            deleteFilesFromChatUseCase(
                params = DeleteFilesRequest(
                    chatId = folderId,
                    messageIds = listOf(file.messageFile.messageId),
                ),
                scope = scope,
            ) { result ->
                result.fold(
                    onSuccess = {
                        dispatch(message = Msg.FileDeleted(messageId = file.messageFile.messageId))
                        removeFromFavorites(index = index, file = file, dispatchChangedFile = false)
                    },
                    onFailure = { error(message = it.getMessage()) },
                )

                dispatch(message = Msg.PendingFileChanged(messageId = null))
            }
        }

        private fun addToFavorites(index: Int, file: TGDriveFile) {
            val favoriteMessage = FavoriteMessage(
                chatId = folderId,
                messageId = file.messageFile.messageId,
            )

            addFavoriteUseCase(
                params = favoriteMessage,
                scope = scope,
            ) { result ->
                result.fold(
                    onSuccess = { id ->
                        dispatch(
                            message = Msg.FileChanged(
                                index = index,
                                file = file.copy(favoriteMessage = favoriteMessage.copy(id = id.toInt()))
                            )
                        )
                    },
                    onFailure = { error(message = it.getMessage()) },
                )
            }
        }

        private fun removeFromFavorites(index: Int, file: TGDriveFile, dispatchChangedFile: Boolean = true) {
            file.favoriteMessage?.let { favoriteMessage ->
                removeSingleFavoriteUseCase(params = favoriteMessage.id, scope = scope) { result ->
                    result.fold(
                        onSuccess = {
                            if (dispatchChangedFile) {
                                dispatch(message = Msg.FileChanged(index = index, file = file.copy(favoriteMessage = null)))
                            }
                        },
                        onFailure = { error(message = it.getMessage()) },
                    )
                }
            }
        }

        private fun error(message: String) = dispatch(message = Msg.Error(message = message))

        private fun calculateFileDownloadProgress(file: File): Float =
            file.local.downloadedSize.toFloat() / file.expectedSize

        private fun calculateFileUploadProgress(file: File): Float =
            file.remote.uploadedSize.toFloat() / file.expectedSize
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {
                is Msg.FilesListLoading -> copy(isLoading = true)
                is Msg.FilesListLoaded -> copy(files = msg.files, errorText = null, isLoading = false)

                is Msg.FileDownloading -> {
                    downloadingFiles[msg.fileId] = .0f
                    copy()
                }

                is Msg.FileDownloadProgressChanged -> {
                    downloadingFiles[msg.fileId] = msg.progress
                    copy()
                }

                is Msg.FileDownloaded -> {
                    files.indexOrNull { file -> file.messageFile.file.id == msg.file.id }?.let { index ->
                        val target = files[index]
                        files[index] = target.copy(messageFile = target.messageFile.copy(file = msg.file))
                        downloadingFiles.remove(key = msg.file.id)
                    }
                    copy()
                }

                is Msg.UpdateBottomSheetToggled -> copy(uploadsBottomSheetActive = !uploadsBottomSheetActive)

                is Msg.FileUploading -> {
                    uploadingFiles.add(element = msg.localFile.copy(id = msg.fileId, uploadProgress = 0f))
                    copy()
                }

                is Msg.FileUploadProgressChanged -> {
                    val index = uploadingFiles.indexOrNull { localFile -> localFile.id == msg.fileId }
                    index?.let { uploadingFiles[index] = uploadingFiles[index].copy(uploadProgress = msg.progress) }
                    copy()
                }

                is Msg.FileUploaded -> {
                    val index = uploadingFiles.indexOrNull { localFile -> localFile.id == msg.fileId }
                    index?.let { uploadingFiles.removeAt(index) }
                    copy()
                }

                is Msg.FileMenuVisibilityChanged -> copy(fileMenuVisible = msg.isVisible)

                is Msg.DeleteFileDialogVisibilityChanged -> copy(deleteFileDialogVisible = msg.isVisible)
                is Msg.FileDeleted -> {
                    val index = files.indexOrNull { file -> file.messageFile.messageId == msg.messageId }
                    index?.let {
                        files.removeAt(index)
                    }
                    copy()
                }

                is Msg.FileChanged -> {
                    files[msg.index] = msg.file
                    copy()
                }

                is Msg.PendingFileChanged -> copy(pendingFileMessageId = msg.messageId)

                is Msg.Error -> copy(errorText = msg.message, isLoading = false)
            }
    }

    private companion object {
        const val STORE_NAME = "FolderStore"
    }
}
