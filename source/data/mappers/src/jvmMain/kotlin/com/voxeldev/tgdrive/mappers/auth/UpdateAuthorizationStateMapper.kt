package com.voxeldev.tgdrive.mappers.auth

import com.voxeldev.tgdrive.auth.api.AuthenticationCodeType
import com.voxeldev.tgdrive.auth.api.AuthenticationState
import org.drinkless.tdlib.TdApi
import org.drinkless.tdlib.TdApi.AuthorizationState

/**
 * @author nvoxel
 */
class UpdateAuthorizationStateMapper {

    fun toResponse(authorizationState: AuthorizationState) =
        when (authorizationState.constructor) {
            TdApi.AuthorizationStateWaitTdlibParameters.CONSTRUCTOR -> AuthenticationState.WaitTdLibParameters
            TdApi.AuthorizationStateWaitPhoneNumber.CONSTRUCTOR -> AuthenticationState.WaitPhoneNumber
            TdApi.AuthorizationStateWaitCode.CONSTRUCTOR -> AuthenticationState.WaitCode(
                codeType = when ((authorizationState as TdApi.AuthorizationStateWaitCode).codeInfo.type.constructor) {
                    TdApi.AuthenticationCodeTypeSms.CONSTRUCTOR -> AuthenticationCodeType.SMS
                    TdApi.AuthenticationCodeTypeCall.CONSTRUCTOR,
                    TdApi.AuthenticationCodeTypeFlashCall.CONSTRUCTOR,
                    TdApi.AuthenticationCodeTypeMissedCall.CONSTRUCTOR,
                    -> AuthenticationCodeType.CALL

                    else -> AuthenticationCodeType.TELEGRAM_MESSAGE
                }
            )

            TdApi.AuthorizationStateWaitOtherDeviceConfirmation.CONSTRUCTOR -> AuthenticationState.WaitConfirmation(
                link = (authorizationState as TdApi.AuthorizationStateWaitOtherDeviceConfirmation).link
            )

            TdApi.AuthorizationStateWaitRegistration.CONSTRUCTOR -> AuthenticationState.WaitRegistration
            TdApi.AuthorizationStateWaitPassword.CONSTRUCTOR -> AuthenticationState.WaitPassword(
                passwordHint = (authorizationState as TdApi.AuthorizationStateWaitPassword).passwordHint
            )

            TdApi.AuthorizationStateReady.CONSTRUCTOR -> AuthenticationState.Ready
            TdApi.AuthorizationStateClosed.CONSTRUCTOR -> AuthenticationState.Closed
            else -> AuthenticationState.Unspecified
        }
}
