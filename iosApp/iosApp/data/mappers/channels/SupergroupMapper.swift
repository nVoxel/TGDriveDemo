//
//  SupergroupMapper.swift
//  iosApp
//
//  Created by nVoxel on 13.05.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import TDLibKit
import TGDriveKit

class SupergroupMapper {
    
    private let photoMapper: PhotoMapper
    
    init(photoMapper: PhotoMapper) {
        self.photoMapper = photoMapper
    }
    
    func toResponse(supergroup: Supergroup) -> CommonSupergroup {
        var isCreator = false
        
        switch (supergroup.status) {
        case .chatMemberStatusCreator(let creatorInfo):
            isCreator = true
        default:
            isCreator = false
        }
        
        return CommonSupergroup(
            supergroupId: supergroup.id,
            username: supergroup.usernames?.editableUsername,
            date: Int32(supergroup.date),
            isCreator: isCreator,
            memberCount: Int32(supergroup.memberCount),
            isChannel: supergroup.isChannel
        )
    }
    
    func toResponse(supergroupFullInfo: SupergroupFullInfo) -> CommonSupergroupFullInfo {
        return CommonSupergroupFullInfo(
            chatPhoto: supergroupFullInfo.photo == nil ? nil : toResponse(chatPhoto: supergroupFullInfo.photo!),
            description: supergroupFullInfo.description,
            chatInviteLink: supergroupFullInfo.inviteLink == nil ? nil : toResponse(chatInviteLink: supergroupFullInfo.inviteLink!)
        )
    }
    
    private func toResponse(chatPhoto: ChatPhoto) -> CommonChatPhoto {
        return CommonChatPhoto(
            id: chatPhoto.id.rawValue,
            addedDate: Int32(chatPhoto.addedDate),
            size: photoMapper.toResponse(sizes: chatPhoto.sizes)
        )
    }
    
    private func toResponse(chatInviteLink: ChatInviteLink) -> CommonChatInviteLink {
        return CommonChatInviteLink(
            link: chatInviteLink.inviteLink,
            name: chatInviteLink.name,
            creatorUserId: chatInviteLink.creatorUserId,
            expirationDate: Int32(chatInviteLink.expirationDate),
            isPrimary: chatInviteLink.isPrimary,
            isRevoked: chatInviteLink.isRevoked
        )
    }
}
