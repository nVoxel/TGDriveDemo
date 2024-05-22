package com.voxeldev.tgdrive.main.api.messages

import com.voxeldev.tgdrive.main.api.media.Audio
import com.voxeldev.tgdrive.main.api.media.Document
import com.voxeldev.tgdrive.main.api.media.Photo
import com.voxeldev.tgdrive.main.api.media.Video
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

/**
 * @author nvoxel
 */
@OptIn(ExperimentalObjCName::class)
@ObjCName(name = "CommonMessageContent")
sealed interface MessageContent {

    data class TextContent(val text: String) : MessageContent

    data class DocumentContent(
        val document: Document,
        val caption: String?,
    ) : MessageContent

    data class PhotoContent(
        val photo: Photo,
        val caption: String?,
        val isSecret: Boolean,
    ) : MessageContent

    data class VideoContent(
        val video: Video,
        val caption: String?,
        val isSecret: Boolean,
    ) : MessageContent

    data class AudioContent(
        val audio: Audio,
        val caption: String?,
    ) : MessageContent
}
