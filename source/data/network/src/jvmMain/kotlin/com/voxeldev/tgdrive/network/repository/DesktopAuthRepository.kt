package com.voxeldev.tgdrive.network.repository

import com.voxeldev.tgdrive.auth.api.AuthRepository
import com.voxeldev.tgdrive.auth.api.TdLibParameters
import kotlinx.telegram.core.TelegramFlow
import kotlinx.telegram.coroutines.checkAuthenticationCode
import kotlinx.telegram.coroutines.checkAuthenticationPassword
import kotlinx.telegram.coroutines.registerUser
import kotlinx.telegram.coroutines.setAuthenticationPhoneNumber
import kotlinx.telegram.coroutines.setTdlibParameters
import org.drinkless.tdlib.TdApi

/**
 * @author nvoxel
 */
internal class DesktopAuthRepository(
    private val telegramFlow: TelegramFlow,
) : AuthRepository {

    override suspend fun setTdLibParameters(tdLibParameters: TdLibParameters) {
        telegramFlow.setTdlibParameters(
            useTestDc = tdLibParameters.useTestDc,
            databaseDirectory = tdLibParameters.databaseDirectory,
            filesDirectory = tdLibParameters.filesDirectory,
            databaseEncryptionKey = null,
            useFileDatabase = tdLibParameters.useFileDatabase,
            useChatInfoDatabase = tdLibParameters.useChatInfoDatabase,
            useMessageDatabase = tdLibParameters.useMessageDatabase,
            useSecretChats = tdLibParameters.useSecretChats,
            apiId = tdLibParameters.apiId,
            apiHash = tdLibParameters.apiHash,
            systemLanguageCode = tdLibParameters.systemLanguageCode,
            deviceModel = tdLibParameters.deviceModel,
            systemVersion = tdLibParameters.systemVersion,
            applicationVersion = tdLibParameters.applicationVersion,
        )
    }

    override suspend fun checkEncryptionKey(encryptionKey: ByteArray?) =
        throw IllegalStateException("Encryption key checks are not supposed to be on desktop platforms")

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
        telegramFlow.registerUser(firstName, lastName, false)
    }
}
