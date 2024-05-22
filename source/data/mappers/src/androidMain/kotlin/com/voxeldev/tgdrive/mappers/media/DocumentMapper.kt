package com.voxeldev.tgdrive.mappers.media

import org.drinkless.td.libcore.telegram.TdApi.Document
import com.voxeldev.tgdrive.main.api.media.Document as CommonDocument

/**
 * @author nvoxel
 */
class DocumentMapper(
    private val thumbnailMapper: ThumbnailMapper,
    private val fileMapper: FileMapper,
) {

    fun toResponse(document: Document): CommonDocument =
        CommonDocument(
            fileName = document.fileName,
            mimeType = document.mimeType,
            thumbnail = document.thumbnail?.let { thumbnailMapper.toResponse(thumbnail = document.thumbnail) },
            file = fileMapper.toResponse(file = document.document)
        )
}
