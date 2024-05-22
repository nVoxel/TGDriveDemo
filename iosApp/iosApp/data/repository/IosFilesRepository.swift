//
//  IosFilesRepository.swift
//  iosApp
//
//  Created by nVoxel on 03.05.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import TDLibKit
import TGDriveKit

class IosFilesRepository : FilesRepository {

    private let clientWrapper: ClientWrapper
    private let messagesRepository: MessagesRepository
    private let fileMapper: FileMapper
    private let messageFileMapper: MessageFileMapper
    
    init(clientWrapper: ClientWrapper, messagesRepository: IosMessagesRepository, fileMapper: FileMapper, messageFileMapper: MessageFileMapper) {
        self.clientWrapper = clientWrapper
        self.messagesRepository = messagesRepository
        self.fileMapper = fileMapper
        self.messageFileMapper = messageFileMapper
    }
    
    func getFilesFromChat(chatId: Int64) async throws -> [CommonMessageFile] {
        let documentMessages = try await messagesRepository.getFullChatHistory(chatId: chatId, onlyDocuments: true)
        return documentMessages.map { messageFileMapper.toResponse(message: $0, chatId: chatId) }.filter { $0 != nil }.map { $0! }
    }
    
    func getFileFromChatMessage(chatId: Int64, messageId: Int64) async throws -> CommonMessageFile {
        let documentMessage = try await messagesRepository.getChatMessage(chatId: chatId, messageId: messageId)
        let mappedValue = messageFileMapper.toResponse(message: documentMessage, chatId: chatId)
        
        if (mappedValue == nil) {
            throw Error(code: 404, message: "This message does not contain a file")
        }
        
        return mappedValue!
    }
    
    func requestFileDownload(fileId: Int32) async throws -> CommonFile {
        let result = try await clientWrapper.client!.downloadFile(
            fileId: Int(fileId),
            limit: 0,
            offset: 0,
            priority: 32,
            synchronous: false
        )
        return fileMapper.toResponse(file: result)
    }
    
    func requestFileUpload(path: String) async throws -> CommonFile {
        let result = try await clientWrapper.client!.preliminaryUploadFile(
            file: InputFile.inputFileLocal(InputFileLocal(path: path)),
            fileType: FileType.fileTypeDocument,
            priority: 32
        )
        return fileMapper.toResponse(file: result)
    }
}
