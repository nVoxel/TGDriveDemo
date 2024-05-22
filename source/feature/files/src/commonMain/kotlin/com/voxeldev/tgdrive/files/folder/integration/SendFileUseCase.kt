package com.voxeldev.tgdrive.files.folder.integration

import com.voxeldev.tgdrive.files.api.file.SendFileRequest
import com.voxeldev.tgdrive.main.api.messages.MessagesRepository
import com.voxeldev.tgdrive.utils.integration.BaseUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * @author nvoxel
 */
internal class SendFileUseCase : BaseUseCase<SendFileRequest, Unit>(), KoinComponent {

    private val messagesRepository: MessagesRepository by inject()

    override suspend fun run(params: SendFileRequest) = messagesRepository.sendFileToChat(chatId = params.chatId, file = params.file)
}
