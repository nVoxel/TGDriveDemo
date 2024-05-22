//
//  ClientWrapper.swift
//  iosApp
//
//  Created by nVoxel on 16.05.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import TGDriveKit
import TDLibKit

class ClientWrapper {

    private let manager = TDLibClientManager()
    var client: TDLibClient? = nil
    
    private lazy var iosTelegramFlow: IosTelegramFlow = IosTelegramFlow(
        restartClientCallback: { self.startClient(shouldRestart: true) }
    )
    
    private lazy var updateHandler: (Data, TDLibClient) -> Void = { data, client in
        do {
            let update = try client.decoder.decode(Update.self, from: data)
            switch update {
            case .updateNewMessage(let newMsg):
                switch newMsg.message.content {
                case .messageText(let text):
                    print("Text Message: \(text.text.text)")
                default:
                    break
                }
            case .updateAuthorizationState(let newState):
                self.iosTelegramFlow.authenticationCallback(self.authStateMapper.toResponse(authorizationState: newState.authorizationState))
            case .updateSupergroup(let supergroupUpdate):
                self.iosTelegramFlow.supergroupCallback(self.supergroupMapper.toResponse(supergroup: supergroupUpdate.supergroup))
            case .updateNewChat(let newChatUpdate):
                self.iosTelegramFlow.chatCallback(self.chatsMapper.toResponse(chat: newChatUpdate.chat))
            case .updateFile(let fileUpdate):
                self.iosTelegramFlow.fileCallback(self.fileMapper.toResponse(file: fileUpdate.file))
            default:
                print("Unhandled Update \(update)")
                break
            }
        } catch {
            print("Error in update handler \(error.localizedDescription)")
        }
    }
    
    private let authStateMapper: AuthStateMapper
    private let fileMapper: FileMapper
    private let thumbnailMapper: ThumbnailMapper
    private let documentMapper: DocumentMapper
    private let photoMapper: PhotoMapper
    private let videoMapper: VideoMapper
    private let audioMapper: AudioMapper
    private let chatsMapper: ChatsMapper
    private let messagesMapper: MessagesMapper
    private let userMapper: UserMapper
    private let supergroupMapper: SupergroupMapper
    private let messageFileMapper: MessageFileMapper
    
    private lazy var authRepository = IosAuthRepository(clientWrapper: self)
    private lazy var chatsRepository = IosChatsRepository(clientWrapper: self)
    private lazy var messagesRepository = IosMessagesRepository(clientWrapper: self, chatsMapper: chatsMapper, messagesMapper: messagesMapper)
    private lazy var filesRepository = IosFilesRepository(clientWrapper: self, messagesRepository: messagesRepository, fileMapper: fileMapper, messageFileMapper: messageFileMapper)
    private lazy var localFilesRepository = IosLocalFilesRepository(documentsDirectory: FileManager.default.urls(for: .documentDirectory, in: .userDomainMask).first!)
    private lazy var profileRepository = IosProfileRepository(clientWrapper: self, userMapper: userMapper)
    private lazy var supergroupsRepository = IosSupergroupsRepository(clientWrapper: self, chatsMapper: chatsMapper, supergroupMapper: supergroupMapper)
    
    lazy var commonDependencies = CommonDependencies(commonTelegramFlow: iosTelegramFlow)
    lazy var repositoryDependencies = RepositoryDependencies(
        authRepository: authRepository,
        chatsRepository: chatsRepository,
        filesRepository: filesRepository,
        localFilesRepository: localFilesRepository,
        messagesRepository: messagesRepository,
        profileRepository: profileRepository,
        supergroupsRepository: supergroupsRepository
    )
    
    init() {
        authStateMapper = AuthStateMapper()
        
        fileMapper = FileMapper()
        thumbnailMapper = ThumbnailMapper(fileMapper: fileMapper)
        documentMapper = DocumentMapper(thumbnailMapper: thumbnailMapper, fileMapper: fileMapper)
        photoMapper = PhotoMapper(fileMapper: fileMapper)
        videoMapper = VideoMapper(fileMapper: fileMapper, thumbnailMapper: thumbnailMapper)
        audioMapper = AudioMapper(fileMapper: fileMapper)
        
        chatsMapper = ChatsMapper(fileMapper: fileMapper)
        
        messagesMapper = MessagesMapper(documentMapper: documentMapper, photoMapper: photoMapper, videoMapper: videoMapper, audioMapper: audioMapper)
        
        userMapper = UserMapper(fileMapper: fileMapper)
        
        supergroupMapper = SupergroupMapper(photoMapper: photoMapper)
        
        messageFileMapper = MessageFileMapper()
    }
    
    func startClient(shouldRestart: Bool) {
        if (client != nil && !shouldRestart) {
            return
        }
        
        client = manager.createClient(updateHandler: updateHandler)
    }
}
