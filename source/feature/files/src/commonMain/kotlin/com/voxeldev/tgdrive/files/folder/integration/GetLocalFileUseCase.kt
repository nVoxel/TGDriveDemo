package com.voxeldev.tgdrive.files.folder.integration

import com.voxeldev.tgdrive.files.api.file.LocalFile
import com.voxeldev.tgdrive.files.api.file.LocalFilesRepository
import com.voxeldev.tgdrive.utils.integration.BaseUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * @author nvoxel
 */
internal class GetLocalFileUseCase : BaseUseCase<String, LocalFile>(), KoinComponent {

    private val localFilesRepository : LocalFilesRepository by inject()

    override suspend fun run(params: String): LocalFile = localFilesRepository.getLocalFilePath(filePointer = params)
}
