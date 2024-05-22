package com.voxeldev.tgdrive.files.folder.integration

import com.voxeldev.tgdrive.files.folder.FolderComponent
import com.voxeldev.tgdrive.files.folder.store.FolderStore.State

/**
 * @author nvoxel
 */
internal class StateMapper(
    private val folderName: String,
) {

    fun toModel(state: State): FolderComponent.Model =
        FolderComponent.Model(
            folderName = folderName,
            files = state.files,
            documentsHash = state.files.hashCode(),
            errorText = state.errorText,
            isLoading = state.isLoading,
            uploadsBottomSheetActive = state.uploadsBottomSheetActive,
            downloadingFiles = state.downloadingFiles,
            downloadingFilesHash = state.downloadingFiles.hashCode(),
            uploadingFiles = state.uploadingFiles,
            uploadingFilesHash = state.uploadingFiles.hashCode(),
            fileMenuVisible = state.fileMenuVisible,
            deleteFileDialogVisible = state.deleteFileDialogVisible,
            pendingFileMessageId = state.pendingFileMessageId ?: Long.MIN_VALUE,
        )
}
