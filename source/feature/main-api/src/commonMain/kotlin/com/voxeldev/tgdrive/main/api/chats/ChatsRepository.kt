package com.voxeldev.tgdrive.main.api.chats

/**
 * @author nvoxel
 */
interface ChatsRepository {

    suspend fun loadChats()

    suspend fun getChatMessageCount(chatId: Long) : Int
}
