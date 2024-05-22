package com.voxeldev.tgdrive.mappers.media

import com.voxeldev.tgdrive.main.api.files.FileLocal
import com.voxeldev.tgdrive.main.api.files.FileRemote
import org.drinkless.tdlib.TdApi.File
import org.drinkless.tdlib.TdApi.LocalFile
import org.drinkless.tdlib.TdApi.RemoteFile
import com.voxeldev.tgdrive.main.api.files.File as CommonFile

/**
 * @author nvoxel
 */
class FileMapper {

    fun toResponse(file: File): CommonFile =
        CommonFile(
            id = file.id,
            size = file.size,
            expectedSize = file.expectedSize,
            local = file.local.toResponse(),
            remote = file.remote.toResponse(),
        )

    private fun LocalFile.toResponse(): FileLocal =
        FileLocal(
            path = path,
            canBeDownloaded = canBeDownloaded,
            canBeDeleted = canBeDeleted,
            isDownloadingActive = isDownloadingActive,
            isDownloadingCompleted = isDownloadingCompleted,
            downloadOffset = downloadOffset,
            downloadedPrefixSize = downloadedPrefixSize,
            downloadedSize = downloadedSize,
        )

    private fun RemoteFile.toResponse(): FileRemote =
        FileRemote(
            id = id,
            uniqueId = uniqueId,
            isUploadingActive = isUploadingActive,
            isUploadingCompleted = isUploadingCompleted,
            uploadedSize = uploadedSize,
        )
}