package com.voxeldev.tgdrive.network.repository

import com.voxeldev.tgdrive.files.api.file.LocalFile
import com.voxeldev.tgdrive.files.api.file.LocalFilesRepository
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

/**
 * @author nvoxel
 */
internal class DesktopLocalFilesRepository(
    private val cacheDirectoryPath: String,
) : LocalFilesRepository {

    override suspend fun getLocalFilePath(filePointer: String): LocalFile {
        val inputFile = File(filePointer)

        val outputFilePath = cacheDirectoryPath + File.separator + inputFile.name
        val outputFile = File(outputFilePath)
        outputFile.parentFile.mkdirs()

        writeFile(inputFile = inputFile, outputFile = outputFile)

        return LocalFile(
            name = inputFile.name,
            path = outputFilePath,
        )
    }

    override suspend fun getSplitFilePaths(filePointer: String): List<LocalFile> {
        TODO("Not yet implemented")
    }

    private fun writeFile(inputFile: File, outputFile: File) {
        val inputStream = FileInputStream(inputFile)
        val outputStream = FileOutputStream(outputFile)

        var read: Int
        val buffers = ByteArray(BUFFER_SIZE)

        while (inputStream.read(buffers).also { read = it } != -1) {
            outputStream.write(buffers, 0, read)
        }

        inputStream.close()
        outputStream.close()
    }

    private companion object {
        const val BUFFER_SIZE = 1024
    }
}
