package com.voxeldev.tgdrive.files.folder.integration

import com.voxeldev.tgdrive.files.api.file.FilesRepository
import com.voxeldev.tgdrive.main.api.files.File
import com.voxeldev.tgdrive.utils.integration.BaseUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * @author nvoxel
 */
internal class DownloadFileUseCase : BaseUseCase<Int, File>(), KoinComponent {

    private val filesRepository: FilesRepository by inject()

    override suspend fun run(params: Int): File = filesRepository.requestFileDownload(fileId = params)
}
