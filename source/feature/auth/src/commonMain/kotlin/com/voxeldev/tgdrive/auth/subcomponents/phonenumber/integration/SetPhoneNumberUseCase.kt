package com.voxeldev.tgdrive.auth.subcomponents.phonenumber.integration

import com.voxeldev.tgdrive.auth.api.AuthRepository
import com.voxeldev.tgdrive.utils.integration.BaseUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * @author nvoxel
 */
internal class SetPhoneNumberUseCase : BaseUseCase<String, Unit>(), KoinComponent {

    private val authRepository: AuthRepository by inject()

    override suspend fun run(params: String) = authRepository.setAuthenticationPhoneNumber(phoneNumber = params)
}
