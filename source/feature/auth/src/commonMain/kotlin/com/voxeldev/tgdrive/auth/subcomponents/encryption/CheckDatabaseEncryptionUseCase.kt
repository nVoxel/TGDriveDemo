package com.voxeldev.tgdrive.auth.subcomponents.encryption

import com.voxeldev.tgdrive.auth.api.AuthRepository
import com.voxeldev.tgdrive.utils.integration.BaseUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * @author nvoxel
 */
internal class CheckDatabaseEncryptionUseCase : BaseUseCase<ByteArray?, Unit>(), KoinComponent {

    private val authRepository: AuthRepository by inject()

    override suspend fun run(params: ByteArray?) = authRepository.checkEncryptionKey(encryptionKey = params)
}
