//
//  AuthRepository.swift
//  iosApp
//
//  Created by nVoxel on 13.04.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import TDLibKit
import TGDriveKit

class IosAuthRepository : AuthRepository {
    
    private let clientWrapper: ClientWrapper
    
    init(clientWrapper: ClientWrapper) {
        self.clientWrapper = clientWrapper
    }
    
    func setTdLibParameters(tdLibParameters: TdLibParameters) async throws {
        try await clientWrapper.client!.setTdlibParameters(
            apiHash: tdLibParameters.apiHash,
            apiId: Int(tdLibParameters.apiId),
            applicationVersion: tdLibParameters.applicationVersion,
            databaseDirectory: tdLibParameters.databaseDirectory,
            databaseEncryptionKey: nil,
            deviceModel: tdLibParameters.deviceModel,
            filesDirectory: tdLibParameters.filesDirectory,
            systemLanguageCode: tdLibParameters.systemLanguageCode,
            systemVersion: tdLibParameters.systemVersion,
            useChatInfoDatabase: tdLibParameters.useChatInfoDatabase,
            useFileDatabase: tdLibParameters.useFileDatabase,
            useMessageDatabase: tdLibParameters.useMessageDatabase,
            useSecretChats: tdLibParameters.useSecretChats,
            useTestDc: tdLibParameters.useTestDc
        )
    }
    
    func checkEncryptionKey(encryptionKey: KotlinByteArray?) async throws {
        // Encryption key checks are not supposed to be on iOS
    }
    
    func setAuthenticationPhoneNumber(phoneNumber: String) async throws {
        try await clientWrapper.client!.setAuthenticationPhoneNumber(
            phoneNumber: phoneNumber,
            settings: nil
        )
    }
    
    func checkAuthenticationCode(code: String) async throws {
        try await clientWrapper.client!.checkAuthenticationCode(code: code)
    }
    
    func checkAuthenticationPassword(password: String) async throws {
        try await clientWrapper.client!.checkAuthenticationPassword(password: password)
    }
    
    func registerUser(firstName: String, lastName: String) async throws {
        try await clientWrapper.client!.registerUser(disableNotification: true, firstName: firstName, lastName: lastName)
    }
}
