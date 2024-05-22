package com.voxeldev.tgdrive.network.repository

import com.voxeldev.tgdrive.main.api.chats.Chat
import com.voxeldev.tgdrive.main.api.files.File
import com.voxeldev.tgdrive.main.api.messages.Message
import com.voxeldev.tgdrive.main.api.messages.MessageContent
import com.voxeldev.tgdrive.main.api.messages.MessagesRepository
import com.voxeldev.tgdrive.mappers.chats.ChatsMapper
import com.voxeldev.tgdrive.mappers.messages.MessagesMapper
import kotlinx.telegram.core.TelegramFlow
import kotlinx.telegram.coroutines.createPrivateChat
import kotlinx.telegram.coroutines.createSupergroupChat
import kotlinx.telegram.coroutines.deleteMessages
import kotlinx.telegram.coroutines.getChatHistory
import kotlinx.telegram.coroutines.getMessage
import kotlinx.telegram.coroutines.sendMessage
import org.drinkless.td.libcore.telegram.TdApi.InputFileId
import org.drinkless.td.libcore.telegram.TdApi.InputMessageDocument

/**
 * @author nvoxel
 */
internal class AndroidMessagesRepository(
    private val telegramFlow: TelegramFlow,
    private val messagesMapper: MessagesMapper,
    private val chatsMapper: ChatsMapper,
) : MessagesRepository {

    override suspend fun getChat(userId: Long): Chat = chatsMapper.toResponse(
        chat = telegramFlow.createPrivateChat(
            userId = userId,
            force = false,
        )
    )

    override suspend fun getChatMessage(chatId: Long, messageId: Long): Message = messagesMapper.toResponse(
        message = telegramFlow.getMessage(chatId = chatId, messageId = messageId)
    )

    override suspend fun getSupergroupChat(supergroupId: Long): Chat = chatsMapper.toResponse(
        chat = telegramFlow.createSupergroupChat(
            supergroupId = supergroupId,
            force = false,
        )
    )

    override suspend fun getChatHistory(chatId: Long, fromMessageId: Long, limit: Int) = messagesMapper.toResponse(
        messages = telegramFlow.getChatHistory(
            chatId = chatId,
            fromMessageId = fromMessageId,
            offset = 0,
            limit = limit,
            onlyLocal = false,
        )
    )

    override suspend fun getFullChatHistory(chatId: Long, onlyDocuments: Boolean): List<Message> {
        val result = mutableListOf<Message>()
        var fromMessageId = 0L

        while (true) {
            val messages = getChatHistory(chatId = chatId, fromMessageId = fromMessageId)
            if (messages.isEmpty()) break

            result.addAll(messages)
            fromMessageId = messages.last().id
        }

        return if (onlyDocuments) result.filter { it.content != null && it.content!!::class in allowedContentTypes }
        else result
    }

    override suspend fun sendFileToChat(chatId: Long, file: File) {
        telegramFlow.sendMessage(
            chatId = chatId,
            messageThreadId = 0,
            replyToMessageId = 0,
            options = null,
            replyMarkup = null,
            inputMessageContent = InputMessageDocument(
                InputFileId(file.id),
                null,
                true,
                null,
            )
        )
    }

    override suspend fun deleteFilesFromChat(chatId: Long, messageIds: List<Long>) {
        telegramFlow.deleteMessages(
            chatId = chatId,
            messageIds = messageIds.toLongArray(),
            revoke = true,
        )
    }

    private companion object {
        val allowedContentTypes = setOf(
            MessageContent.DocumentContent::class,
            MessageContent.PhotoContent::class,
            MessageContent.VideoContent::class,
            MessageContent.AudioContent::class,
        )
    }
}
