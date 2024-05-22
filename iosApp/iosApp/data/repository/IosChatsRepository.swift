//
//  IosChatsRepository.swift
//  iosApp
//
//  Created by nVoxel on 13.05.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import TDLibKit
import TGDriveKit

class IosChatsRepository : ChatsRepository {
    
    private let clientWrapper: ClientWrapper
    
    init(clientWrapper: ClientWrapper) {
        self.clientWrapper = clientWrapper
    }
    
    func loadChats() async throws {
        try await clientWrapper.client!.loadChats(chatList: nil, limit: 100)
    }
    
    func getChatMessageCount(chatId: Int64) async throws -> KotlinInt {
        let result = try await clientWrapper.client!.getChatMessageCount(
            chatId: chatId,
            filter: SearchMessagesFilter.searchMessagesFilterDocument,
            returnLocal: false,
            savedMessagesTopicId: 0
        )
        return KotlinInt(int: Int32(result.count))
    }
}
