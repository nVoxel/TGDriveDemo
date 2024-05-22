package com.voxeldev.tgdrive.main.api.messages

import com.voxeldev.tgdrive.main.api.chats.Chat
import com.voxeldev.tgdrive.main.api.files.File

/**
 * @author nvoxel
 */
interface MessagesRepository {

    suspend fun getChat(userId: Long): Chat

    suspend fun getChatMessage(chatId: Long, messageId: Long) : Message

    suspend fun getSupergroupChat(supergroupId: Long) : Chat

    suspend fun getChatHistory(chatId: Long, fromMessageId: Long, limit: Int = 100): List<Message>

    suspend fun getFullChatHistory(chatId: Long, onlyDocuments: Boolean): List<Message>

    suspend fun sendFileToChat(chatId: Long, file: File)

    suspend fun deleteFilesFromChat(chatId: Long, messageIds: List<Long>)
}
