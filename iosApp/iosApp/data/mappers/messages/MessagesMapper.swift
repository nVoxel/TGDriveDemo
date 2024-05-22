//
//  MessagesMapper.swift
//  iosApp
//
//  Created by nVoxel on 25.04.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import TDLibKit
import TGDriveKit

class MessagesMapper {
    
    private let documentMapper: DocumentMapper
    private let photoMapper: PhotoMapper
    private let videoMapper: VideoMapper
    private let audioMapper: AudioMapper
    
    init(documentMapper: DocumentMapper, photoMapper: PhotoMapper, videoMapper: VideoMapper, audioMapper: AudioMapper) {
        self.documentMapper = documentMapper
        self.photoMapper = photoMapper
        self.videoMapper = videoMapper
        self.audioMapper = audioMapper
    }
    
    func toResponse(messages: Messages) -> [CommonMessage] {
        return messages.messages != nil ? messages.messages!.map { toResponse(message: $0) } : []
    }
    
    func toResponse(message: Message) -> CommonMessage {
        return CommonMessage(
            id: message.id,
            messageSender: toResponse(messageSender: message.senderId),
            chatId: message.chatId,
            isOutgoing: message.isOutgoing,
            isPinned: message.isPinned,
            canBeEdited: message.canBeEdited,
            canBeForwarded: message.canBeForwarded,
            canBeSaved: message.canBeSaved,
            canBeDeletedOnlyForSelf: message.canBeDeletedOnlyForSelf,
            canBeDeletedForAllUsers: message.canBeDeletedForAllUsers,
            canGetMessageThread: message.canGetMessageThread,
            canGetViewers: message.canGetViewers,
            isChannelPost: message.isChannelPost,
            containsUnreadMention: message.containsUnreadMention,
            date: Int32(message.date),
            editDate: Int32(message.editDate),
            content: toResponse(messageContent: message.content)
        )
    }
    
    private func toResponse(messageSender: MessageSender) -> CommonMessageSender {
        switch messageSender {
        case .messageSenderUser(let userSender):
            return CommonMessageSenderUser(userId: userSender.userId)
        case .messageSenderChat(let chatSender):
            return CommonMessageSenderChat(chatId: chatSender.chatId)
        }
    }
    
    private func toResponse(messageContent: MessageContent) -> CommonMessageContent? {
        switch messageContent {
        case .messageText(let textMessage):
            return CommonMessageContentTextContent(text: textMessage.text.text)
        case .messageDocument(let documentMessage):
            return CommonMessageContentDocumentContent(
                document: documentMapper.toResponse(document: documentMessage.document),
                caption: documentMessage.caption.text
            )
        case .messagePhoto(let photoMessage):
            return CommonMessageContentPhotoContent(
                photo: photoMapper.toResponse(photo: photoMessage.photo),
                caption: photoMessage.caption.text,
                isSecret: photoMessage.isSecret
            )
        case .messageVideo(let videoMessage):
            return CommonMessageContentVideoContent(
                video: videoMapper.toResponse(video: videoMessage.video),
                caption: videoMessage.caption.text, 
                isSecret: videoMessage.isSecret
            )
        case .messageAudio(let audioMessage):
            return CommonMessageContentAudioContent(
                audio: audioMapper.toResponse(audio: audioMessage.audio),
                caption: audioMessage.caption.text
            )
        default:
            return nil
        }
    }
}
