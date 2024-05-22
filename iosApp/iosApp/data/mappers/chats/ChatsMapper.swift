//
//  ChatsMapper.swift
//  iosApp
//
//  Created by nVoxel on 25.04.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import TDLibKit
import TGDriveKit

class ChatsMapper {
    
    private let fileMapper: FileMapper
    
    init(fileMapper: FileMapper) {
        self.fileMapper = fileMapper
    }
    
    func toResponse(chat: Chat) -> CommonChat {
        return CommonChat(
            id: chat.id,
            type: toResponse(chatType: chat.type),
            title: chat.title,
            photo: chat.photo != nil ? toResponse(chatPhotoInfo: chat.photo!) : nil,
            hasProtectedContent: chat.hasProtectedContent
        )
    }
    
    private func toResponse(chatType: ChatType) -> CommonChatType {
        switch chatType {
        case .chatTypePrivate(let privateChat):
            return CommonChatTypePrivate(userId: privateChat.userId)
        case .chatTypeBasicGroup(let basisGroupChat):
            return CommonChatTypeBasicGroup(basicGroupId: basisGroupChat.basicGroupId)
        case .chatTypeSupergroup(let supergroupChat):
            return CommonChatTypeSupergroup(supergroupId: supergroupChat.supergroupId, isChannel: supergroupChat.isChannel)
        case .chatTypeSecret(let secretChat):
            return CommonChatTypeSecret(secretChatId: Int64(secretChat.secretChatId), userId: secretChat.userId)
        }
    }
    
    private func toResponse(chatPhotoInfo: ChatPhotoInfo) -> CommonChatPhotoInfo {
        return CommonChatPhotoInfo(
            small: fileMapper.toResponse(file: chatPhotoInfo.small),
            big: fileMapper.toResponse(file: chatPhotoInfo.big)
        )
    }
}
