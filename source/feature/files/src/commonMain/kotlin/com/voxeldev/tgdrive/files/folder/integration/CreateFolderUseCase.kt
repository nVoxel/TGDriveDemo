package com.voxeldev.tgdrive.files.folder.integration

import com.voxeldev.tgdrive.files.api.channels.CreateFolderRequest
import com.voxeldev.tgdrive.files.api.channels.SupergroupsRepository
import com.voxeldev.tgdrive.main.api.chats.Chat
import com.voxeldev.tgdrive.utils.integration.BaseUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * @author nvoxel
 */
class CreateFolderUseCase : BaseUseCase<CreateFolderRequest, Chat>(), KoinComponent {

    private val supergroupsRepository : SupergroupsRepository by inject()

    override suspend fun run(params: CreateFolderRequest): Chat =
        supergroupsRepository.createChannel(title = params.title, description = params.description)
}
