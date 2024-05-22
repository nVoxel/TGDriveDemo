package com.voxeldev.tgdrive.mappers.media

import org.drinkless.td.libcore.telegram.TdApi.Photo
import org.drinkless.td.libcore.telegram.TdApi.PhotoSize
import com.voxeldev.tgdrive.main.api.media.Photo as CommonPhoto
import com.voxeldev.tgdrive.main.api.media.PhotoSize as CommonPhotoSize

/**
 * @author nvoxel
 */
class PhotoMapper(
    private val fileMapper: FileMapper,
) {

    fun toResponse(photo: Photo): CommonPhoto =
        CommonPhoto(sizes = toResponse(sizes = photo.sizes))

    fun toResponse(sizes: Array<PhotoSize>): List<CommonPhotoSize> =
        sizes.map { it.toResponse() }

    private fun PhotoSize.toResponse(): CommonPhotoSize =
        CommonPhotoSize(
            type = type,
            photo = fileMapper.toResponse(file = photo),
            width = width,
            height = height,
        )
}
