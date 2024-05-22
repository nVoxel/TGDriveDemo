package com.voxeldev.tgdrive.auth.api

/**
 * @author nvoxel
 */
interface AuthRepository {

    suspend fun setTdLibParameters(tdLibParameters: TdLibParameters)

    suspend fun checkEncryptionKey(encryptionKey: ByteArray?)

    suspend fun setAuthenticationPhoneNumber(phoneNumber: String)

    suspend fun checkAuthenticationCode(code: String)

    suspend fun checkAuthenticationPassword(password: String)

    suspend fun registerUser(firstName: String, lastName: String)
}
