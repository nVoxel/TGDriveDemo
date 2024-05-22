package com.voxeldev.tgdrive.files.list.integration

import com.voxeldev.tgdrive.files.api.channels.SupergroupFullInfo
import com.voxeldev.tgdrive.files.api.channels.SupergroupsRepository
import com.voxeldev.tgdrive.utils.integration.BaseUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * @author nvoxel
 */
internal class GetSupergroupFullInfoUseCase : BaseUseCase<Long, SupergroupFullInfo>(), KoinComponent {

    private val supergroupsRepository: SupergroupsRepository by inject()

    override suspend fun run(params: Long): SupergroupFullInfo = supergroupsRepository.getSupergroupFullInfo(supergroupId = params)
}
