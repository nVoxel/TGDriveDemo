package com.voxeldev.tgdrive.network.repository

import com.voxeldev.tgdrive.main.api.chats.ChatsRepository
import kotlinx.telegram.core.TelegramFlow
import kotlinx.telegram.coroutines.getChatMessageCount
import kotlinx.telegram.coroutines.loadChats
import org.drinkless.td.libcore.telegram.TdApi.SearchMessagesFilterDocument

/**
 * @author nvoxel
 */
class AndroidChatsRepository(
    private val telegramFlow: TelegramFlow,
) : ChatsRepository {

    override suspend fun loadChats() {
        telegramFlow.loadChats(chatList = null, limit = 100)
    }

    override suspend fun getChatMessageCount(chatId: Long): Int =
        telegramFlow.getChatMessageCount(
            chatId = chatId,
            filter = SearchMessagesFilterDocument(),
            returnLocal = false,
        ).count
}
