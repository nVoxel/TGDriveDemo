//
//  IosSupergroupsRepository.swift
//  iosApp
//
//  Created by nVoxel on 13.05.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import TDLibKit
import TGDriveKit

class IosSupergroupsRepository : SupergroupsRepository {
    
    private let clientWrapper: ClientWrapper
    private let chatsMapper: ChatsMapper
    private let supergroupMapper: SupergroupMapper
    
    init(clientWrapper: ClientWrapper, chatsMapper: ChatsMapper, supergroupMapper: SupergroupMapper) {
        self.clientWrapper = clientWrapper
        self.chatsMapper = chatsMapper
        self.supergroupMapper = supergroupMapper
    }
    
    func createChannel(title: String, description: String) async throws -> CommonChat {
        let result = try await clientWrapper.client!.createNewSupergroupChat(
            description: description,
            forImport: false,
            isChannel: true,
            isForum: false,
            location: nil,
            messageAutoDeleteTime: 0,
            title: title
        )
        return chatsMapper.toResponse(chat: result)
    }
    
    func getSupergroup(supergroupId: Int64) async throws -> CommonSupergroup {
        let result = try await clientWrapper.client!.getSupergroup(supergroupId: supergroupId)
        return supergroupMapper.toResponse(supergroup: result)
    }
    
    func getSupergroupFullInfo(supergroupId: Int64) async throws -> CommonSupergroupFullInfo {
        let result = try await clientWrapper.client!.getSupergroupFullInfo(supergroupId: supergroupId)
        return supergroupMapper.toResponse(supergroupFullInfo: result)
    }
    
    func renameSupergroup(chatId: Int64, title: String) async throws {
        try await clientWrapper.client!.setChatTitle(chatId: chatId, title: title)
    }
    
    func deleteSupergroup(chatId: Int64) async throws {
        try await clientWrapper.client!.deleteChat(chatId: chatId)
    }
}
