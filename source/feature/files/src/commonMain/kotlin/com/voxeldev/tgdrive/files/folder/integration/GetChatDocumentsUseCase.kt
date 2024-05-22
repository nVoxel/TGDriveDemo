package com.voxeldev.tgdrive.files.folder.integration

import com.voxeldev.tgdrive.files.api.file.MessageFile
import com.voxeldev.tgdrive.files.api.file.FilesRepository
import com.voxeldev.tgdrive.utils.integration.BaseUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * @author nvoxel
 */
internal class GetChatDocumentsUseCase : BaseUseCase<Long, List<MessageFile>>(), KoinComponent {

    private val filesRepository: FilesRepository by inject()

    override suspend fun run(params: Long) = filesRepository.getFilesFromChat(chatId = params)
}
