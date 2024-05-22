package com.voxeldev.tgdrive.files.list.integration

import com.voxeldev.tgdrive.files.api.channels.SupergroupsRepository
import com.voxeldev.tgdrive.utils.integration.BaseUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * @author nvoxel
 */
class DeleteFolderUseCase : BaseUseCase<Long, Unit>(), KoinComponent {

    private val supergroupsRepository : SupergroupsRepository by inject()

    override suspend fun run(params: Long) = supergroupsRepository.deleteSupergroup(chatId = params)

}
