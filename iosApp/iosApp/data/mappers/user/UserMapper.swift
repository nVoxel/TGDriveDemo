//
//  UserMapper.swift
//  iosApp
//
//  Created by nVoxel on 25.04.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import TDLibKit
import TGDriveKit

class UserMapper {
    
    private let fileMapper: FileMapper
    
    init(fileMapper: FileMapper) {
        self.fileMapper = fileMapper
    }
    
    func toResponse(user: User) -> CommonProfile {
        return CommonProfile(
            id: user.id,
            firstName: user.firstName,
            lastName: user.lastName,
            username: user.usernames!.editableUsername,
            phoneNumber: user.phoneNumber,
            profilePhoto: user.profilePhoto != nil ? toReponse(profilePhoto: user.profilePhoto!) : nil,
            isContact: user.isContact,
            isMutualContact: user.isMutualContact,
            isVerified: user.isVerified,
            isSupport: user.isSupport,
            restrictionReason: user.restrictionReason,
            isScam: user.isScam,
            isFake: user.isFake,
            haveAccess: user.haveAccess,
            languageCode: user.languageCode
        )
    }
    
    private func toReponse(profilePhoto: ProfilePhoto) -> CommonProfilePhoto {
        return CommonProfilePhoto(
            id: profilePhoto.id.rawValue,
            small: fileMapper.toResponse(file: profilePhoto.small),
            big: fileMapper.toResponse(file: profilePhoto.big)
        )
    }
    
}
