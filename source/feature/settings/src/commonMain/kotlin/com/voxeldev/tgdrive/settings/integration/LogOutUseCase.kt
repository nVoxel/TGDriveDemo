package com.voxeldev.tgdrive.settings.integration

import com.voxeldev.tgdrive.main.api.profile.ProfileRepository
import com.voxeldev.tgdrive.utils.integration.BaseUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * @author nvoxel
 */
class LogOutUseCase : BaseUseCase<BaseUseCase.NoParams, Unit>(), KoinComponent {

    private val profileRepository: ProfileRepository by inject()

    override suspend fun run(params: NoParams) = profileRepository.logOut()
}
