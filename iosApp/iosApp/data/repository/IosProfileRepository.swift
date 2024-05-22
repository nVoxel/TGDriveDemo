//
//  IosProfileRepository.swift
//  iosApp
//
//  Created by nVoxel on 25.04.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import TDLibKit
import TGDriveKit

class IosProfileRepository : ProfileRepository {
    
    private let clientWrapper: ClientWrapper
    private let userMapper: UserMapper
    
    init(clientWrapper: ClientWrapper, userMapper: UserMapper) {
        self.clientWrapper = clientWrapper
        self.userMapper = userMapper
    }
    
    func getMe() async throws -> CommonProfile {
        let result = try await clientWrapper.client!.getMe()
        return userMapper.toResponse(user: result)
    }
    
    func getUser(userId: Int64) async throws -> CommonProfile {
        let result = try await clientWrapper.client!.getUser(userId: userId)
        return userMapper.toResponse(user: result)
    }
    
    func logOut() async throws {
        try await clientWrapper.client!.logOut()
    }
}
