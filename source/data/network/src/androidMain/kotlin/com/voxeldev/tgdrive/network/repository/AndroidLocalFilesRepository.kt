package com.voxeldev.tgdrive.network.repository

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import com.voxeldev.tgdrive.files.api.file.LocalFile
import com.voxeldev.tgdrive.files.api.file.LocalFilesRepository
import com.voxeldev.tgdrive.utils.exceptions.FileReadError
import java.io.File
import java.io.FileOutputStream

/**
 * @author nvoxel
 */
internal class AndroidLocalFilesRepository(
    private val context: Context,
    private val contentResolver: ContentResolver = context.contentResolver,
) : LocalFilesRepository {

    override suspend fun getLocalFilePath(filePointer: String): LocalFile {
        val fileUri = Uri.parse(filePointer)
        val returnCursor = getReturnCursor(uri = fileUri) ?: throw FileReadError

        with (returnCursor) {
            val nameColumnIndex = getColumnIndex(OpenableColumns.DISPLAY_NAME)
            val sizeColumnIndex = getColumnIndex(OpenableColumns.SIZE)

            moveToFirst()
            val name = getString(nameColumnIndex)
            val size = getLong(sizeColumnIndex)

            val outputFilePath = context.cacheDir.toString() + File.separator + name
            val outputFile = File(outputFilePath)

            writeFile(fileUri = fileUri, outputFile = outputFile)

            return LocalFile(
                name = name,
                path = outputFilePath,
            )
        }
    }

    override suspend fun getSplitFilePaths(filePointer: String): List<LocalFile> {
        TODO("Not yet implemented")
    }

    private fun getReturnCursor(uri: Uri): Cursor? = contentResolver.query(
        uri, arrayOf(OpenableColumns.DISPLAY_NAME, OpenableColumns.SIZE), null, null
    )

    private fun writeFile(fileUri: Uri, outputFile: File) {
        val inputStream = contentResolver.openInputStream(fileUri) ?: throw FileReadError
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
