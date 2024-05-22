package com.voxeldev.tgdrive.files.api.file

/**
 * @author nvoxel
 */
interface LocalFilesRepository {

    suspend fun getLocalFilePath(filePointer: String) : LocalFile

    suspend fun getSplitFilePaths(filePointer: String) : List<LocalFile>
}
