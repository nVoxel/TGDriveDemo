//
//  AuthStateMapper.swift
//  iosApp
//
//  Created by nVoxel on 25.04.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import TDLibKit
import TGDriveKit

class AuthStateMapper {
    
    func toResponse(authorizationState: AuthorizationState) -> AuthenticationState {
        switch authorizationState {
        case .authorizationStateWaitTdlibParameters:
            return AuthenticationStateWaitTdLibParameters()
        case .authorizationStateWaitPhoneNumber:
            return AuthenticationStateWaitPhoneNumber()
        case .authorizationStateWaitCode(let state):
            return AuthenticationStateWaitCode(codeType: toResponse(codeType: state.codeInfo.type))
        case .authorizationStateWaitOtherDeviceConfirmation(let state):
            return AuthenticationStateWaitConfirmation(link: state.link)
        case .authorizationStateWaitRegistration:
            return AuthenticationStateWaitRegistration()
        case .authorizationStateWaitPassword(let state):
            return AuthenticationStateWaitPassword(passwordHint: state.passwordHint)
        case .authorizationStateReady:
            return AuthenticationStateReady()
        case .authorizationStateClosed:
            return AuthenticationStateClosed()
        default:
            return AuthenticationStateUnspecified()
        }
    }
    
    private func toResponse(codeType: AuthenticationCodeType) -> CommonAuthenticationCodeType {
        switch codeType {
        case .authenticationCodeTypeSms:
            return CommonAuthenticationCodeType.sms
        case .authenticationCodeTypeCall, .authenticationCodeTypeFlashCall, .authenticationCodeTypeMissedCall:
            return CommonAuthenticationCodeType.call
        default:
            return CommonAuthenticationCodeType.telegramMessage
        }
    }
}
