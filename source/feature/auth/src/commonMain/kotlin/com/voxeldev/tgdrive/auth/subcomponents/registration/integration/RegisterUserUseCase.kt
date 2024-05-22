package com.voxeldev.tgdrive.auth.subcomponents.registration.integration

import com.voxeldev.tgdrive.auth.api.AuthRepository
import com.voxeldev.tgdrive.utils.integration.BaseUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * @author nvoxel
 */
internal class RegisterUserUseCase : BaseUseCase<Pair<String, String>, Unit>(), KoinComponent {

    private val authRepository: AuthRepository by inject()

    override suspend fun run(params: Pair<String, String>) = authRepository.registerUser(
        firstName = params.first,
        lastName = params.second,
    )
}
