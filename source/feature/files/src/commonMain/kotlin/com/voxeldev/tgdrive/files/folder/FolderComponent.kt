package com.voxeldev.tgdrive.files.folder

import com.arkivanov.decompose.value.Value
import com.voxeldev.tgdrive.files.api.file.LocalFile
import com.voxeldev.tgdrive.files.api.file.TGDriveFile

/**
 * @author nvoxel
 */
interface FolderComponent {

    val model: Value<Model>

    fun onFileClicked(id: Int)

    fun onFileFavoriteClicked(messageId: Long)

    fun onReloadClicked()

    fun onUploadsBottomSheetToggle()
    fun onUploadFileSelect()
    fun onUploadFileSelected(filePointer: String)

    fun onOpenFileMenuClicked(messageId: Long)
    fun onDismissFileMenuClicked()

    fun onDeleteFileClicked()
    fun onConfirmDeleteFileClicked()
    fun onDismissDeleteFileClicked()

    fun onCloseClicked()

    data class Model(
        val folderName: String,
        val files: List<TGDriveFile>,
        val documentsHash: Int,
        val errorText: String?,
        val isLoading: Boolean,
        val uploadsBottomSheetActive: Boolean,
        val downloadingFiles: Map<Int, Float>,
        val downloadingFilesHash: Int,
        val uploadingFiles: List<LocalFile>,
        val uploadingFilesHash: Int,
        val fileMenuVisible: Boolean,
        val deleteFileDialogVisible: Boolean,
        val pendingFileMessageId: Long,
    )
}
