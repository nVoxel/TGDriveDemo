package com.voxeldev.tgdrive.files.list.store

import com.arkivanov.mvikotlin.core.store.Store
import com.voxeldev.tgdrive.files.api.folder.Folder
import com.voxeldev.tgdrive.files.list.store.FoldersListStore.Intent
import com.voxeldev.tgdrive.files.list.store.FoldersListStore.State

/**
 * @author nvoxel
 */
internal interface FoldersListStore : Store<Intent, State, Nothing> {

    sealed interface Intent {
        data object Reload : Intent

        data class OpenFolderMenu(val chatId: Long) : Intent
        data object DismissFolderMenu : Intent

        data object CreateFolder : Intent
        data object ConfirmCreateFolder : Intent
        data object DismissCreateFolder : Intent

        data object RenameFolder : Intent
        data object ConfirmRenameFolder : Intent
        data object DismissRenameFolder : Intent

        data object DeleteFolder : Intent
        data object ConfirmDeleteFolder : Intent
        data object DismissDeleteFolder : Intent

        data class SetFolderTitle(val title: String) : Intent
    }

    data class State(
        val folders: MutableList<Folder> = mutableListOf(),
        val errorText: String? = null,
        val isLoading: Boolean = false,
        val folderMenuVisible: Boolean = false,
        val createFolderDialogVisible: Boolean = false,
        val renameFolderDialogVisible: Boolean = false,
        val deleteFolderDialogVisible: Boolean = false,
        val pendingFolderChatId: Long? = null,
        val pendingFolderTitle: String = "",
    )
}
