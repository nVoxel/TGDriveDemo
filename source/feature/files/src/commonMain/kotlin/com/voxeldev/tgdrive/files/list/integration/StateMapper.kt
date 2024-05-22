package com.voxeldev.tgdrive.files.list.integration

import com.voxeldev.tgdrive.files.list.FoldersListComponent
import com.voxeldev.tgdrive.files.list.store.FoldersListStore.State

/**
 * @author nvoxel
 */
internal class StateMapper {

    fun toModel(state: State) : FoldersListComponent.Model =
        FoldersListComponent.Model(
            folders = state.folders,
            foldersHash = state.folders.hashCode(),
            errorText = state.errorText,
            isLoading = state.isLoading,
            folderMenuVisible = state.folderMenuVisible,
            createFolderDialogVisible = state.createFolderDialogVisible,
            renameFolderDialogVisible = state.renameFolderDialogVisible,
            deleteFolderDialogVisible = state.deleteFolderDialogVisible,
            pendingFolderTitle = state.pendingFolderTitle,
            pendingFolderChatId = state.pendingFolderChatId ?: Long.MIN_VALUE,
        )
}
