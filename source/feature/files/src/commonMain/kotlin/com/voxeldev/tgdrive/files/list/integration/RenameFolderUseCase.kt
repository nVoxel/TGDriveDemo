package com.voxeldev.tgdrive.files.list.integration

import com.voxeldev.tgdrive.files.api.channels.SupergroupsRepository
import com.voxeldev.tgdrive.files.api.file.RenameFolderRequest
import com.voxeldev.tgdrive.utils.integration.BaseUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * @author nvoxel
 */
class RenameFolderUseCase : BaseUseCase<RenameFolderRequest, Unit>(), KoinComponent {

    private val supergroupsRepository: SupergroupsRepository by inject()

    override suspend fun run(params: RenameFolderRequest) =
        supergroupsRepository.renameSupergroup(chatId = params.chatId, title = params.title)

}
