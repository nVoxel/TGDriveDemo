package com.voxeldev.tgdrive.auth.api

/**
 * @author nvoxel
 */
sealed interface AuthenticationState {
    data object WaitTdLibParameters : AuthenticationState
    data object WaitEncryptionKey : AuthenticationState
    data object WaitPhoneNumber : AuthenticationState
    data class WaitCode(val codeType: AuthenticationCodeType) : AuthenticationState
    data class WaitConfirmation(val link: String) : AuthenticationState
    data object WaitRegistration : AuthenticationState
    data class WaitPassword(val passwordHint: String) : AuthenticationState
    data object Ready : AuthenticationState
    data object Closed : AuthenticationState
    data object Unspecified : AuthenticationState
}
