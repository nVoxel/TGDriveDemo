package com.voxeldev.tgdrive.files.list.integration

import com.voxeldev.tgdrive.main.api.chats.Chat
import com.voxeldev.tgdrive.main.api.messages.MessagesRepository
import com.voxeldev.tgdrive.utils.integration.BaseUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * @author nvoxel
 */
class GetSupergroupChatUseCase : BaseUseCase<Long, Chat>(), KoinComponent {

    private val messagesRepository: MessagesRepository by inject()

    override suspend fun run(params: Long): Chat = messagesRepository.getSupergroupChat(supergroupId = params)
}
