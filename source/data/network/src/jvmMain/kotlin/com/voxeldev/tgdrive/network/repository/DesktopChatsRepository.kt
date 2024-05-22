package com.voxeldev.tgdrive.network.repository

import com.voxeldev.tgdrive.main.api.chats.ChatsRepository
import kotlinx.telegram.core.TelegramFlow
import kotlinx.telegram.coroutines.getChatMessageCount
import kotlinx.telegram.coroutines.loadChats
import org.drinkless.tdlib.TdApi.SearchMessagesFilterDocument

/**
 * @author nvoxel
 */
internal class DesktopChatsRepository(
    private val telegramFlow: TelegramFlow,
) : ChatsRepository {

    override suspend fun loadChats() {
        telegramFlow.loadChats(chatList = null, limit = 100)
    }

    override suspend fun getChatMessageCount(chatId: Long): Int =
        telegramFlow.getChatMessageCount(
            chatId = chatId,
            filter = SearchMessagesFilterDocument(),
            savedMessagesTopicId = 0,
            returnLocal = false,
        ).count
}
