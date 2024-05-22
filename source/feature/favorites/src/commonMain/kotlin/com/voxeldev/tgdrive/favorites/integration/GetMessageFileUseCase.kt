package com.voxeldev.tgdrive.favorites.integration

import com.voxeldev.tgdrive.favorites.api.GetMessageRequest
import com.voxeldev.tgdrive.files.api.file.FilesRepository
import com.voxeldev.tgdrive.files.api.file.MessageFile
import com.voxeldev.tgdrive.utils.integration.BaseUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * @author nvoxel
 */
class GetMessageFileUseCase : BaseUseCase<GetMessageRequest, MessageFile>(), KoinComponent {

    private val filesRepository: FilesRepository by inject()

    override suspend fun run(params: GetMessageRequest): MessageFile =
        filesRepository.getFileFromChatMessage(chatId = params.chatId, messageId = params.messageId)
}
