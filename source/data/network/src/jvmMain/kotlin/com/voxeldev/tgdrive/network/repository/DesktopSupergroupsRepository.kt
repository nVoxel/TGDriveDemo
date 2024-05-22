package com.voxeldev.tgdrive.network.repository

import com.voxeldev.tgdrive.files.api.channels.Supergroup
import com.voxeldev.tgdrive.files.api.channels.SupergroupsRepository
import com.voxeldev.tgdrive.main.api.chats.Chat
import com.voxeldev.tgdrive.mappers.channels.SupergroupMapper
import com.voxeldev.tgdrive.mappers.chats.ChatsMapper
import kotlinx.telegram.core.TelegramFlow
import kotlinx.telegram.coroutines.createNewSupergroupChat
import kotlinx.telegram.coroutines.deleteChat
import kotlinx.telegram.coroutines.getSupergroup
import kotlinx.telegram.coroutines.getSupergroupFullInfo
import kotlinx.telegram.coroutines.setChatTitle

/**
 * @author nvoxel
 */
internal class DesktopSupergroupsRepository(
    private val telegramFlow: TelegramFlow,
    private val chatsMapper: ChatsMapper,
    private val supergroupMapper: SupergroupMapper,
) : SupergroupsRepository {

    override suspend fun createChannel(title: String, description: String): Chat = chatsMapper.toResponse(
        chat = telegramFlow.createNewSupergroupChat(
            title = title,
            isForum = false,
            isChannel = true,
            description = description,
            location = null,
            messageAutoDeleteTime = 0,
            forImport = false,
        )
    )

    override suspend fun getSupergroup(supergroupId: Long): Supergroup = supergroupMapper.toResponse(
        supergroup = telegramFlow.getSupergroup(supergroupId = supergroupId),
    )

    override suspend fun getSupergroupFullInfo(supergroupId: Long) = supergroupMapper.toResponse(
        supergroupFullInfo = telegramFlow.getSupergroupFullInfo(supergroupId = supergroupId),
    )

    override suspend fun renameSupergroup(chatId: Long, title: String) {
        telegramFlow.setChatTitle(chatId = chatId, title = title)
    }

    override suspend fun deleteSupergroup(chatId: Long) {
        telegramFlow.deleteChat(chatId = chatId)
    }
}
