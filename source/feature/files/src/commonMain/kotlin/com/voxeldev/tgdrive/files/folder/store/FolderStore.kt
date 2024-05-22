package com.voxeldev.tgdrive.files.folder.store

import com.arkivanov.mvikotlin.core.store.Store
import com.voxeldev.tgdrive.files.api.file.LocalFile
import com.voxeldev.tgdrive.files.api.file.TGDriveFile
import com.voxeldev.tgdrive.files.folder.store.FolderStore.Intent
import com.voxeldev.tgdrive.files.folder.store.FolderStore.State

/**
 * @author nvoxel
 */
internal interface FolderStore : Store<Intent, State, Nothing> {

    sealed interface Intent {
        data class FileClicked(val id: Int) : Intent

        data class FileFavoriteClicked(val messageId: Long) : Intent

        data object Reload : Intent

        data object UploadsBottomSheetToggle : Intent
        data class UploadFile(val filePointer: String) : Intent

        data class OpenFileMenu(val messageId: Long) : Intent
        data object DismissFileMenu : Intent

        data object DeleteFile : Intent
        data object ConfirmDeleteFile : Intent
        data object DismissDeleteFile : Intent
    }

    data class State(
        val files: MutableList<TGDriveFile> = mutableListOf(),
        val errorText: String? = null,
        val isLoading: Boolean = false,
        val uploadsBottomSheetActive: Boolean = false,
        val downloadingFiles: HashMap<Int, Float> = hashMapOf(),
        val uploadingFiles: MutableList<LocalFile> = mutableListOf(),
        val fileMenuVisible: Boolean = false,
        val deleteFileDialogVisible: Boolean = false,
        val pendingFileMessageId: Long? = null,
    )
}
