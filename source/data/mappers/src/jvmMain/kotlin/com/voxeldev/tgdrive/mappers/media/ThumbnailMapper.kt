package com.voxeldev.tgdrive.mappers.media

import org.drinkless.tdlib.TdApi.Thumbnail
import com.voxeldev.tgdrive.main.api.media.Thumbnail as CommonThumbnail

/**
 * @author nvoxel
 */
class ThumbnailMapper(
    private val fileMapper: FileMapper,
) {

    fun toResponse(thumbnail: Thumbnail): CommonThumbnail =
        CommonThumbnail(
            width = thumbnail.width,
            height = thumbnail.height,
            file = fileMapper.toResponse(file = thumbnail.file)
        )
}
