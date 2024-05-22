package com.voxeldev.tgdrive.files.list.integration

import com.voxeldev.tgdrive.main.api.chats.ChatsRepository
import com.voxeldev.tgdrive.utils.integration.BaseUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * @author nvoxel
 */
internal class LoadChatsUseCase : BaseUseCase<BaseUseCase.NoParams, Unit>(), KoinComponent {

    private val chatsRepository : ChatsRepository by inject()

    override suspend fun run(params: NoParams) = chatsRepository.loadChats()
}
