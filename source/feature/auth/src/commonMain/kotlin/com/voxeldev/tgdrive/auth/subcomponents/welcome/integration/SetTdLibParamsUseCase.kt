package com.voxeldev.tgdrive.auth.subcomponents.welcome.integration

import com.voxeldev.tgdrive.auth.api.AuthRepository
import com.voxeldev.tgdrive.auth.api.TdLibParameters
import com.voxeldev.tgdrive.utils.integration.BaseUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * @author nvoxel
 */
internal class SetTdLibParamsUseCase : BaseUseCase<TdLibParameters, Unit>(), KoinComponent {

    private val authRepository: AuthRepository by inject()

    override suspend fun run(params: TdLibParameters) = authRepository.setTdLibParameters(tdLibParameters = params)
}
