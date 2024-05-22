package com.voxeldev.tgdrive.auth.subcomponents.password.integration

import com.voxeldev.tgdrive.auth.api.AuthRepository
import com.voxeldev.tgdrive.utils.integration.BaseUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * @author nvoxel
 */
internal class CheckPasswordUseCase : BaseUseCase<String, Unit>(), KoinComponent {

    private val authRepository: AuthRepository by inject()

    override suspend fun run(params: String) = authRepository.checkAuthenticationPassword(password = params)
}
