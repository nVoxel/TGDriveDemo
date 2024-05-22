package com.voxeldev.tgdrive.files.api.file

import com.voxeldev.tgdrive.main.api.files.File

/**
 * @author nvoxel
 */
interface FilesRepository {

    suspend fun getFilesFromChat(chatId: Long) : List<MessageFile>

    suspend fun getFileFromChatMessage(chatId: Long, messageId: Long) : MessageFile

    suspend fun requestFileDownload(fileId: Int) : File

    suspend fun requestFileUpload(path: String) : File
}
