package com.voxeldev.tgdrive.network.repository

import com.voxeldev.tgdrive.auth.api.AuthRepository
import com.voxeldev.tgdrive.auth.api.TdLibParameters
import com.voxeldev.tgdrive.mappers.auth.TdLibParametersMapper
import kotlinx.telegram.core.TelegramFlow
import kotlinx.telegram.coroutines.checkAuthenticationCode
import kotlinx.telegram.coroutines.checkAuthenticationPassword
import kotlinx.telegram.coroutines.checkDatabaseEncryptionKey
import kotlinx.telegram.coroutines.registerUser
import kotlinx.telegram.coroutines.setAuthenticationPhoneNumber
import kotlinx.telegram.coroutines.setTdlibParameters
import org.drinkless.td.libcore.telegram.TdApi

/**
 * @author nvoxel
 */
internal class AndroidAuthRepository(
    private val telegramFlow: TelegramFlow,
    private val tdLibParametersMapper: TdLibParametersMapper,
) : AuthRepository {

    override suspend fun setTdLibParameters(tdLibParameters: TdLibParameters) {
        telegramFlow.setTdlibParameters(tdLibParametersMapper.toRequest(tdLibParameters))
    }

    override suspend fun checkEncryptionKey(encryptionKey: ByteArray?) {
        telegramFlow.checkDatabaseEncryptionKey(encryptionKey)
    }


    override suspend fun setAuthenticationPhoneNumber(phoneNumber: String) {
        telegramFlow.setAuthenticationPhoneNumber(phoneNumber, TdApi.PhoneNumberAuthenticationSettings())
    }

    override suspend fun checkAuthenticationCode(code: String) {
        telegramFlow.checkAuthenticationCode(code)
    }

    override suspend fun checkAuthenticationPassword(password: String) {
        telegramFlow.checkAuthenticationPassword(password)
    }

    override suspend fun registerUser(firstName: String, lastName: String) {
        telegramFlow.registerUser(firstName, lastName)
    }
}
