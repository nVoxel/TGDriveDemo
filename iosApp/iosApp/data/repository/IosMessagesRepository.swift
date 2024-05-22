//
//  IosMessagesRepository.swift
//  iosApp
//
//  Created by nVoxel on 25.04.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import TDLibKit
import TGDriveKit

class IosMessagesRepository : MessagesRepository {
    
    private let clientWrapper: ClientWrapper
    private let chatsMapper: ChatsMapper
    private let messagesMapper: MessagesMapper
    
    init(clientWrapper: ClientWrapper, chatsMapper: ChatsMapper, messagesMapper: MessagesMapper) {
        self.clientWrapper = clientWrapper
        self.chatsMapper = chatsMapper
        self.messagesMapper = messagesMapper
    }
    
    func getChat(userId: Int64) async throws -> CommonChat {
        let result = try await clientWrapper.client!.createPrivateChat(force: false, userId: userId)
        return chatsMapper.toResponse(chat: result)
    }
    
    func getChatMessage(chatId: Int64, messageId: Int64) async throws -> CommonMessage {
        let result = try await clientWrapper.client!.getMessage(chatId: chatId, messageId: messageId)
        return messagesMapper.toResponse(message: result)
    }
    
    func getSupergroupChat(supergroupId: Int64) async throws -> CommonChat {
        let result = try await clientWrapper.client!.createSupergroupChat(force: false, supergroupId: supergroupId)
        return chatsMapper.toResponse(chat: result)
    }
    
    func getChatHistory(chatId: Int64, fromMessageId: Int64, limit: Int32) async throws -> [CommonMessage] {
        let result = try await clientWrapper.client!.getChatHistory(
            chatId: chatId,
            fromMessageId: fromMessageId,
            limit: Int(limit),
            offset: 0,
            onlyLocal: false
        )
        return messagesMapper.toResponse(messages: result)
    }
    
    func getFullChatHistory(chatId: Int64, onlyDocuments: Bool) async throws -> [CommonMessage] {
        var result = [CommonMessage]()
        var fromMessageId: Int64 = 0
        
        while true {
            let messages = try await getChatHistory(chatId: chatId, fromMessageId: fromMessageId, limit: 100)
            if messages.isEmpty { break }
            
            result.append(contentsOf: messages)
            fromMessageId = messages.last!.id
        }
        
        return onlyDocuments ? result.filter { 
            $0.content is CommonMessageContentDocumentContent ||
            $0.content is CommonMessageContentPhotoContent ||
            $0.content is CommonMessageContentVideoContent ||
            $0.content is CommonMessageContentAudioContent
        } : result
    }
    
    func sendFileToChat(chatId: Int64, file: CommonFile) async throws {
        let _ = try await clientWrapper.client!.sendMessage(
            chatId: chatId,
            inputMessageContent: InputMessageContent.inputMessageDocument(
                InputMessageDocument(
                    caption: nil,
                    disableContentTypeDetection: true,
                    document: InputFile.inputFileId(InputFileId(id: Int(file.id))),
                    thumbnail: nil
                )
            ),
            messageThreadId: nil,
            options: nil,
            replyMarkup: nil,
            replyTo: nil
        )
    }
    
    func deleteFilesFromChat(chatId: Int64, messageIds: [KotlinLong]) async throws {
        try await clientWrapper.client!.deleteMessages(
            chatId: chatId,
            messageIds: messageIds.map { Int64(truncating: $0) },
            revoke: true
        )
    }
}
