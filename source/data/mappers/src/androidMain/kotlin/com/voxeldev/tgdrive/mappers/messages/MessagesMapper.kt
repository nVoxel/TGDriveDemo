package com.voxeldev.tgdrive.mappers.messages

import com.voxeldev.tgdrive.mappers.media.AudioMapper
import com.voxeldev.tgdrive.mappers.media.DocumentMapper
import com.voxeldev.tgdrive.mappers.media.PhotoMapper
import com.voxeldev.tgdrive.mappers.media.VideoMapper
import org.drinkless.td.libcore.telegram.TdApi.Message
import org.drinkless.td.libcore.telegram.TdApi.MessageAudio
import org.drinkless.td.libcore.telegram.TdApi.MessageContent
import org.drinkless.td.libcore.telegram.TdApi.MessageDocument
import org.drinkless.td.libcore.telegram.TdApi.MessagePhoto
import org.drinkless.td.libcore.telegram.TdApi.MessageSender
import org.drinkless.td.libcore.telegram.TdApi.MessageSenderChat
import org.drinkless.td.libcore.telegram.TdApi.MessageSenderUser
import org.drinkless.td.libcore.telegram.TdApi.MessageText
import org.drinkless.td.libcore.telegram.TdApi.MessageVideo
import org.drinkless.td.libcore.telegram.TdApi.Messages
import com.voxeldev.tgdrive.main.api.messages.Message as CommonMessage
import com.voxeldev.tgdrive.main.api.messages.MessageContent as CommonMessageContent
import com.voxeldev.tgdrive.main.api.messages.MessageSender as CommonMessageSender

/**
 * @author nvoxel
 */
class MessagesMapper(
    private val documentMapper: DocumentMapper,
    private val photoMapper: PhotoMapper,
    private val videoMapper: VideoMapper,
    private val audioMapper: AudioMapper,
) {

    fun toResponse(messages: Messages): List<CommonMessage> =
        messages.messages?.map { toResponse(message = it) } ?: emptyList()

    fun toResponse(message: Message): CommonMessage =
        CommonMessage(
            id = message.id,
            messageSender = message.senderId.toResponse(),
            chatId = message.chatId,
            isOutgoing = message.isOutgoing,
            isPinned = message.isPinned,
            canBeEdited = message.canBeEdited,
            canBeForwarded = message.canBeForwarded,
            canBeSaved = message.canBeSaved,
            canBeDeletedOnlyForSelf = message.canBeDeletedOnlyForSelf,
            canBeDeletedForAllUsers = message.canBeDeletedForAllUsers,
            canGetMessageThread = message.canGetMessageThread,
            canGetViewers = message.canGetViewers,
            isChannelPost = message.isChannelPost,
            containsUnreadMention = message.containsUnreadMention,
            date = message.date,
            editDate = message.editDate,
            content = message.content.toResponse(),
        )

    private fun MessageSender.toResponse(): CommonMessageSender? =
        when (this) {
            is MessageSenderUser -> CommonMessageSender.User(userId = userId)
            is MessageSenderChat -> CommonMessageSender.Chat(chatId = chatId)
            else -> null
        }

    private fun MessageContent.toResponse(): CommonMessageContent? =
        when (this) {
            is MessageText -> CommonMessageContent.TextContent(text = text.text)
            is MessageDocument -> CommonMessageContent.DocumentContent(
                document = documentMapper.toResponse(document = document),
                caption = caption.text,
            )

            is MessagePhoto -> CommonMessageContent.PhotoContent(
                photo = photoMapper.toResponse(photo = photo),
                caption = caption.text,
                isSecret = isSecret,
            )

            is MessageVideo -> CommonMessageContent.VideoContent(
                video = videoMapper.toResponse(video = video),
                caption = caption.text,
                isSecret = isSecret,
            )

            is MessageAudio -> CommonMessageContent.AudioContent(
                audio = audioMapper.toResponse(audio = audio),
                caption = caption.text,
            )

            else -> null
        }
}
