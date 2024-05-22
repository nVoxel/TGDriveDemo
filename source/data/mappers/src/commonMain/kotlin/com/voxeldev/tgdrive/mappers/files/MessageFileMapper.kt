package com.voxeldev.tgdrive.mappers.files

import com.voxeldev.tgdrive.files.api.file.MessageFile
import com.voxeldev.tgdrive.main.api.messages.MessageContent
import com.voxeldev.tgdrive.main.api.messages.Message as CommonMessage

/**
 * @author nvoxel
 */
class MessageFileMapper {

    fun toResponse(message: CommonMessage, chatId: Long): MessageFile? =
        message.content.run {
            when (this) {
                is MessageContent.DocumentContent -> toResponse(messageId = message.id, chatId = chatId)
                is MessageContent.PhotoContent -> toResponse(messageId = message.id, chatId = chatId)
                is MessageContent.VideoContent -> toResponse(messageId = message.id, chatId = chatId)
                is MessageContent.AudioContent -> toResponse(messageId = message.id, chatId = chatId)
                else -> null
            }
        }

    private fun MessageContent.DocumentContent.toResponse(messageId: Long, chatId: Long): MessageFile =
        MessageFile(
            chatId = chatId,
            messageId = messageId,
            fileName = document.fileName,
            file = document.file,
            caption = caption,
        )

    private fun MessageContent.PhotoContent.toResponse(messageId: Long, chatId: Long): MessageFile =
        MessageFile(
            chatId = chatId,
            messageId = messageId,
            fileName = "Compressed Photo",
            file = photo.sizes.last().photo,
            caption = caption,
        )

    private fun MessageContent.VideoContent.toResponse(messageId: Long, chatId: Long): MessageFile =
        MessageFile(
            chatId = chatId,
            messageId = messageId,
            fileName = video.fileName,
            file = video.video,
            caption = caption,
        )

    private fun MessageContent.AudioContent.toResponse(messageId: Long, chatId: Long): MessageFile =
        MessageFile(
            chatId = chatId,
            messageId = messageId,
            fileName = audio.fileName,
            file = audio.file,
            caption = caption,
        )
}
