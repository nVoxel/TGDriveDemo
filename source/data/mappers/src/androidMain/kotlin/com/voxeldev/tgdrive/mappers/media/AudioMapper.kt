package com.voxeldev.tgdrive.mappers.media

import org.drinkless.td.libcore.telegram.TdApi.Audio
import com.voxeldev.tgdrive.main.api.media.Audio as CommonAudio

/**
 * @author nvoxel
 */
class AudioMapper(
    private val fileMapper: FileMapper,
) {

    fun toResponse(audio: Audio): CommonAudio =
        CommonAudio(
            duration = audio.duration,
            title = audio.title,
            performer = audio.performer,
            fileName = audio.fileName,
            mimeType = audio.mimeType,
            file = fileMapper.toResponse(file = audio.audio),
        )
}
