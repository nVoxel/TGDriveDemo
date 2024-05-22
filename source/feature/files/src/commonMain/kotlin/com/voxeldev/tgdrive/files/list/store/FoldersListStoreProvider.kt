package com.voxeldev.tgdrive.files.list.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.voxeldev.tgdrive.files.api.channels.CreateFolderRequest
import com.voxeldev.tgdrive.files.api.channels.Supergroup
import com.voxeldev.tgdrive.files.api.channels.SupergroupFullInfo
import com.voxeldev.tgdrive.files.api.file.RenameFolderRequest
import com.voxeldev.tgdrive.files.api.folder.Folder
import com.voxeldev.tgdrive.files.folder.integration.CreateFolderUseCase
import com.voxeldev.tgdrive.files.list.integration.DeleteFolderUseCase
import com.voxeldev.tgdrive.files.list.integration.GetChatMessageCountUseCase
import com.voxeldev.tgdrive.files.list.integration.GetSupergroupChatUseCase
import com.voxeldev.tgdrive.files.list.integration.GetSupergroupFullInfoUseCase
import com.voxeldev.tgdrive.files.list.integration.GetSupergroupUseCase
import com.voxeldev.tgdrive.files.list.integration.LoadChatsUseCase
import com.voxeldev.tgdrive.files.list.integration.RemoveFolderFavoritesUseCase
import com.voxeldev.tgdrive.files.list.integration.RenameFolderUseCase
import com.voxeldev.tgdrive.files.list.store.FoldersListStore.Intent
import com.voxeldev.tgdrive.files.list.store.FoldersListStore.State
import com.voxeldev.tgdrive.main.api.chats.Chat
import com.voxeldev.tgdrive.main.api.chats.ChatType
import com.voxeldev.tgdrive.utils.extensions.getMessage
import com.voxeldev.tgdrive.utils.extensions.indexOrNull
import com.voxeldev.tgdrive.utils.integration.BaseUseCase
import kotlinx.coroutines.launch
import kotlinx.telegram.core.CommonTelegramFlow

/**
 * @author nvoxel
 */
internal class FoldersListStoreProvider(
    private val storeFactory: StoreFactory,
    private val commonTelegramFlow: CommonTelegramFlow,
    private val loadChatsUseCase: LoadChatsUseCase = LoadChatsUseCase(),
    private val getSupergroupUseCase: GetSupergroupUseCase = GetSupergroupUseCase(),
    private val getSupergroupChatUseCase: GetSupergroupChatUseCase = GetSupergroupChatUseCase(),
    private val getSupergroupFullInfoUseCase: GetSupergroupFullInfoUseCase = GetSupergroupFullInfoUseCase(),
    private val getChatMessageCountUseCase: GetChatMessageCountUseCase = GetChatMessageCountUseCase(),
    private val createFolderUseCase: CreateFolderUseCase = CreateFolderUseCase(),
    private val renameFolderUseCase: RenameFolderUseCase = RenameFolderUseCase(),
    private val deleteFolderUseCase: DeleteFolderUseCase = DeleteFolderUseCase(),
    private val removeFolderFavoritesUseCase: RemoveFolderFavoritesUseCase = RemoveFolderFavoritesUseCase(),
) {

    fun provide(): FoldersListStore =
        object :
            FoldersListStore,
            Store<Intent, State, Nothing> by storeFactory.create(
                name = STORE_NAME,
                initialState = State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl,
            ) {}

    private sealed interface Msg {
        data class ReceivedFolder(val folder: Folder) : Msg

        data object FoldersListInitialLoading : Msg
        data object FoldersListInitialLoaded : Msg

        data class FolderMenuVisibilityChanged(val isVisible: Boolean) : Msg

        data class CreateFolderDialogVisibilityChanged(val isVisible: Boolean) : Msg
        data class RenameFolderDialogVisibilityChanged(val isVisible: Boolean) : Msg
        data class DeleteFolderDialogVisibilityChanged(val isVisible: Boolean) : Msg

        data class FolderRenamed(val chatId: Long, val newName: String) : Msg
        data class FolderDeleted(val chatId: Long) : Msg

        data class PendingFolderChanged(val chatId: Long?) : Msg
        data class FolderPendingTitleChanged(val title: String) : Msg

        data class Error(val message: String) : Msg
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Unit, State, Msg, Nothing>() {

        override fun executeAction(action: Unit, getState: () -> State) {
            scope.launch {
                commonTelegramFlow.commonSupergroupFlow().collect {
                    if (it.isChannel) getSupergroupChat(supergroup = it)
                    loadFolders()
                }
            }

            dispatch(message = Msg.FoldersListInitialLoading)
            loadFolders()
        }

        override fun executeIntent(intent: Intent, getState: () -> State) {
            val state = getState()

            when (intent) {
                is Intent.Reload -> {
                    dispatch(message = Msg.FoldersListInitialLoading)
                    loadFolders()
                }

                is Intent.OpenFolderMenu -> {
                    dispatch(message = Msg.FolderMenuVisibilityChanged(isVisible = true))
                    dispatch(message = Msg.PendingFolderChanged(chatId = intent.chatId))
                }
                is Intent.DismissFolderMenu -> {
                    dispatch(message = Msg.FolderMenuVisibilityChanged(isVisible = false))
                    // TODO: SwiftUI confirmationDialog sends dismiss after its item was clicked
                    // dispatch(message = Msg.PendingFolderChanged(chatId = null))
                }

                is Intent.CreateFolder -> dispatch(message = Msg.CreateFolderDialogVisibilityChanged(isVisible = true))
                is Intent.ConfirmCreateFolder -> createFolder(title = state.pendingFolderTitle)
                is Intent.DismissCreateFolder -> {
                    dispatch(message = Msg.CreateFolderDialogVisibilityChanged(isVisible = false))
                    dispatch(message = Msg.FolderPendingTitleChanged(title = ""))
                    dispatch(message = Msg.PendingFolderChanged(chatId = null))
                }

                is Intent.RenameFolder -> {
                    dispatch(message = Msg.FolderMenuVisibilityChanged(isVisible = false))
                    dispatch(message = Msg.RenameFolderDialogVisibilityChanged(isVisible = true))
                }
                is Intent.ConfirmRenameFolder -> renameFolder(chatId = state.pendingFolderChatId, title = state.pendingFolderTitle)
                is Intent.DismissRenameFolder -> {
                    dispatch(message = Msg.RenameFolderDialogVisibilityChanged(isVisible = false))
                    dispatch(message = Msg.FolderPendingTitleChanged(title = ""))
                    dispatch(message = Msg.PendingFolderChanged(chatId = null))
                }

                is Intent.DeleteFolder -> {
                    dispatch(message = Msg.FolderMenuVisibilityChanged(isVisible = false))
                    dispatch(message = Msg.DeleteFolderDialogVisibilityChanged(isVisible = true))
                }
                is Intent.ConfirmDeleteFolder -> deleteFolder(chatId = state.pendingFolderChatId)
                is Intent.DismissDeleteFolder -> {
                    dispatch(message = Msg.DeleteFolderDialogVisibilityChanged(isVisible = false))
                    dispatch(message = Msg.PendingFolderChanged(chatId = null))
                }

                is Intent.SetFolderTitle -> dispatch(message = Msg.FolderPendingTitleChanged(title = intent.title))
            }
        }

        private fun loadFolders() {
            loadChatsUseCase(params = BaseUseCase.NoParams, scope = scope) {
                dispatch(message = Msg.FoldersListInitialLoaded)
            }
        }

        private fun getSupergroup(chat: Chat) {
            (chat.type as? ChatType.Supergroup)?.let { supergroupChat ->
                getSupergroupUseCase(params = supergroupChat.supergroupId, scope = scope) { result ->
                    result.fold(
                        onSuccess = { if (it.isChannel) getSupergroupFullInfo(chat = chat, supergroup = it) },
                        onFailure = { error(message = it.getMessage()) }
                    )
                }
            }
        }

        private fun getSupergroupChat(supergroup: Supergroup) {
            getSupergroupChatUseCase(params = supergroup.supergroupId, scope = scope) { result ->
                result.onSuccess { getSupergroupFullInfo(chat = it, supergroup = supergroup) }
            }
        }

        private fun getSupergroupFullInfo(chat: Chat, supergroup: Supergroup) {
            getSupergroupFullInfoUseCase(params = supergroup.supergroupId, scope = scope) { result ->
                result.fold(
                    onSuccess = {
                        if (it.description.contains(TGDRIVE_FOLDER_MARKER)) {
                            getMessageCount(chat = chat, supergroup = supergroup, supergroupFullInfo = it)
                        }
                    },
                    onFailure = { error(message = it.getMessage()) },
                )
            }
        }

        private fun getMessageCount(
            chat: Chat,
            supergroup: Supergroup,
            supergroupFullInfo: SupergroupFullInfo,
        ) {
            getChatMessageCountUseCase(params = chat.id, scope = scope) { result ->
                result.fold(
                    onSuccess = {
                        dispatch(
                            message = Msg.ReceivedFolder(
                                folder = Folder(
                                    supergroup = supergroup,
                                    chatId = chat.id,
                                    title = chat.title,
                                    description = supergroupFullInfo.description,
                                    fileCount = it,
                                    inviteLink = supergroupFullInfo.chatInviteLink,
                                )
                            )
                        )
                    },
                    onFailure = { error(message = it.getMessage()) },
                )
            }
        }

        private fun createFolder(title: String) {
            if (title.isBlank()) {
                error(message = "Invalid folder name")
                return
            }

            dispatch(message = Msg.CreateFolderDialogVisibilityChanged(isVisible = false))
            dispatch(message = Msg.FolderPendingTitleChanged(title = ""))

            createFolderUseCase(
                params = CreateFolderRequest(
                    title = title,
                    description = TGDRIVE_FOLDER_MARKER,
                ),
                scope = scope,
            ) { result ->
                result.fold(
                    onSuccess = { getSupergroup(chat = it) },
                    onFailure = { error(message = it.getMessage()) }
                )
            }
        }

        private fun renameFolder(chatId: Long?, title: String) {
            dispatch(message = Msg.RenameFolderDialogVisibilityChanged(isVisible = false))
            dispatch(message = Msg.FolderPendingTitleChanged(title = ""))

            chatId?.let {
                renameFolderUseCase(
                    params = RenameFolderRequest(
                        chatId = chatId,
                        title = title,
                    ),
                    scope = scope,
                ) { result ->
                    result.fold(
                        onSuccess = { dispatch(message = Msg.FolderRenamed(chatId = chatId, newName = title)) },
                        onFailure = { error(message = it.getMessage()) },
                    )

                    dispatch(message = Msg.PendingFolderChanged(chatId = null))
                }
            }
        }

        private fun deleteFolder(chatId: Long?) {
            dispatch(message = Msg.DeleteFolderDialogVisibilityChanged(isVisible = false))

            chatId?.let {
                deleteFolderUseCase(params = chatId, scope = scope) { result ->
                    result.fold(
                        onSuccess = {
                            dispatch(message = Msg.FolderDeleted(chatId = chatId))
                            deleteFolderFavorites(chatId = chatId)
                        },
                        onFailure = { error(message = it.getMessage()) },
                    )

                    dispatch(message = Msg.PendingFolderChanged(chatId = null))
                }
            }
        }

        private fun deleteFolderFavorites(chatId: Long) {
            removeFolderFavoritesUseCase(params = chatId, scope = scope) { result ->
                result.onFailure { error(message = it.getMessage()) }
            }
        }

        private fun error(message: String) = dispatch(message = Msg.Error(message = message))
    }

    private object ReducerImpl : Reducer<State, Msg> {

        override fun State.reduce(msg: Msg): State =
            when (msg) {
                is Msg.ReceivedFolder -> {
                    if (folders.find { it.chatId == msg.folder.chatId } == null) {
                        folders.add(msg.folder)
                    }
                    copy()
                }

                is Msg.FoldersListInitialLoading -> copy(isLoading = true, errorText = null)
                is Msg.FoldersListInitialLoaded -> copy(isLoading = false)

                is Msg.FolderMenuVisibilityChanged -> copy(folderMenuVisible = msg.isVisible)

                is Msg.CreateFolderDialogVisibilityChanged -> copy(createFolderDialogVisible = msg.isVisible)
                is Msg.RenameFolderDialogVisibilityChanged -> copy(renameFolderDialogVisible = msg.isVisible)
                is Msg.DeleteFolderDialogVisibilityChanged -> copy(deleteFolderDialogVisible = msg.isVisible)

                is Msg.FolderRenamed -> {
                    val index = folders.indexOrNull { folder -> folder.chatId == msg.chatId }
                    index?.let {
                        folders[index] = folders[index].copy(title = msg.newName)
                    }
                    copy()
                }

                is Msg.FolderDeleted -> {
                    val index = folders.indexOrNull { folder -> folder.chatId == msg.chatId }
                    index?.let {
                        folders.removeAt(index = index)
                    }
                    copy()
                }

                is Msg.PendingFolderChanged -> copy(pendingFolderChatId = msg.chatId)
                is Msg.FolderPendingTitleChanged -> copy(pendingFolderTitle = msg.title)

                is Msg.Error -> copy(errorText = msg.message)
            }
    }

    private companion object {
        const val STORE_NAME = "FoldersListStore"
        const val TGDRIVE_FOLDER_MARKER = "#TGDriveFolder"
    }
}
