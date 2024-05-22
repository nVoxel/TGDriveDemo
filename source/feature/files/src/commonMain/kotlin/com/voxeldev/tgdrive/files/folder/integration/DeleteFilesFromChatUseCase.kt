package com.voxeldev.tgdrive.files.folder.integration

import com.voxeldev.tgdrive.files.api.file.DeleteFilesRequest
import com.voxeldev.tgdrive.main.api.messages.MessagesRepository
import com.voxeldev.tgdrive.utils.integration.BaseUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * @author nvoxel
 */
class DeleteFilesFromChatUseCase : BaseUseCase<DeleteFilesRequest, Unit>(), KoinComponent {

    private val messagesRepository: MessagesRepository by inject()

    override suspend fun run(params: DeleteFilesRequest) =
        messagesRepository.deleteFilesFromChat(chatId = params.chatId, messageIds = params.messageIds)
}
