package com.voxeldev.tgdrive.files.list

import com.arkivanov.decompose.value.Value
import com.voxeldev.tgdrive.files.api.folder.Folder
import com.voxeldev.tgdrive.main.api.chats.ChatInviteLink

/**
 * @author nvoxel
 */
interface FoldersListComponent {

    val model: Value<Model>

    fun onFolderClicked(id: Long, name: String)

    fun onReloadClicked()

    fun onOpenFolderMenuClicked(chatId: Long)
    fun onDismissFolderMenuClicked()

    fun onCreateFolderClicked()
    fun onConfirmCreateFolderClicked()
    fun onDismissCreateFolderClicked()

    fun onRenameFolderClicked()
    fun onConfirmRenameFolderClicked()
    fun onDismissRenameFolderClicked()

    fun onDeleteFolderClicked()
    fun onConfirmDeleteFolderClicked()
    fun onDismissDeleteFolderClicked()

    fun onShareFolderClicked(chatInviteLink: ChatInviteLink)

    fun onFolderTitleUpdate(title: String)

    data class Model(
        val folders: List<Folder>,
        val foldersHash: Int,
        val errorText: String?,
        val isLoading: Boolean,
        val folderMenuVisible: Boolean,
        val createFolderDialogVisible: Boolean,
        val renameFolderDialogVisible: Boolean,
        val deleteFolderDialogVisible: Boolean,
        val pendingFolderTitle: String,
        val pendingFolderChatId: Long,
    )

    sealed interface Output {
        data class Selected(
            val folderId: Long,
            val folderName: String,
        ) : Output
    }
}
