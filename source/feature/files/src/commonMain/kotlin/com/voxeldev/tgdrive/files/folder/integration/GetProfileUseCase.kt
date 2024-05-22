package com.voxeldev.tgdrive.files.folder.integration

import com.voxeldev.tgdrive.main.api.profile.Profile
import com.voxeldev.tgdrive.main.api.profile.ProfileRepository
import com.voxeldev.tgdrive.utils.integration.BaseUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * @author nvoxel
 */
internal class GetProfileUseCase : BaseUseCase<BaseUseCase.NoParams, Profile>(), KoinComponent {

    private val profileRepository: ProfileRepository by inject()

    override suspend fun run(params: NoParams) = profileRepository.getMe()
}
