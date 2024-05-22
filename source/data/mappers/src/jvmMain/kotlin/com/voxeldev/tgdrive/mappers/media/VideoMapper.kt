package com.voxeldev.tgdrive.mappers.media

import org.drinkless.tdlib.TdApi.Video
import com.voxeldev.tgdrive.main.api.media.Video as CommonVideo

/**
 * @author nvoxel
 */
class VideoMapper(
    private val thumbnailMapper: ThumbnailMapper,
    private val fileMapper: FileMapper,
) {

    fun toResponse(video: Video): CommonVideo =
        CommonVideo(
            duration = video.duration,
            width = video.width,
            height = video.height,
            fileName = video.fileName,
            mimeType = video.mimeType,
            supportsStreaming = video.supportsStreaming,
            thumbnail = video.thumbnail?.let { thumbnailMapper.toResponse(thumbnail = video.thumbnail) },
            video = fileMapper.toResponse(file = video.video),
        )
}
