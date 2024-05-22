package com.voxeldev.tgdrive.network.repository

import com.voxeldev.tgdrive.files.api.file.MessageFile
import com.voxeldev.tgdrive.files.api.file.FilesRepository
import com.voxeldev.tgdrive.main.api.files.File
import com.voxeldev.tgdrive.main.api.messages.MessagesRepository
import com.voxeldev.tgdrive.mappers.files.MessageFileMapper
import com.voxeldev.tgdrive.mappers.media.FileMapper
import com.voxeldev.tgdrive.utils.exceptions.MessageFileNotFound
import kotlinx.telegram.core.TelegramFlow
import kotlinx.telegram.coroutines.downloadFile
import kotlinx.telegram.coroutines.uploadFile
import org.drinkless.td.libcore.telegram.TdApi.FileTypeDocument
import org.drinkless.td.libcore.telegram.TdApi.InputFileLocal

/**
 * @author nvoxel
 */
internal class AndroidFilesRepository(
    private val telegramFlow: TelegramFlow,
    private val messagesRepository: MessagesRepository,
    private val fileMapper: FileMapper,
    private val messageFileMapper: MessageFileMapper,
) : FilesRepository {

    override suspend fun getFilesFromChat(chatId: Long): List<MessageFile> {
        val documentMessages = messagesRepository.getFullChatHistory(chatId = chatId, onlyDocuments = true)
        return documentMessages.mapNotNull { message -> messageFileMapper.toResponse(message = message, chatId = chatId) }
    }

    override suspend fun getFileFromChatMessage(chatId: Long, messageId: Long): MessageFile {
        val documentMessage = messagesRepository.getChatMessage(chatId = chatId, messageId = messageId)
        return messageFileMapper.toResponse(message = documentMessage, chatId = chatId) ?: throw MessageFileNotFound
    }

    override suspend fun requestFileDownload(fileId: Int) : File = fileMapper.toResponse(
        file = telegramFlow.downloadFile(
            fileId = fileId,
            priority = DOWNLOAD_PRIORITY,
            offset = 0,
            limit = 0,
            synchronous = false,
        )
    )

    override suspend fun requestFileUpload(path: String) : File = fileMapper.toResponse(
        file = telegramFlow.uploadFile(
            file = InputFileLocal(path),
            fileType = FileTypeDocument(),
            priority = UPLOAD_PRIORITY,
        )
    )

    private companion object {
        const val DOWNLOAD_PRIORITY = 32
        const val UPLOAD_PRIORITY = 32
    }
}
