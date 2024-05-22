package com.voxeldev.tgdrive.files.api.file

import com.voxeldev.tgdrive.main.api.files.File
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

/**
 * @author nvoxel
 */
@OptIn(ExperimentalObjCName::class)
@ObjCName(name = "CommonMessageFile")
data class MessageFile(
    val chatId: Long,
    val messageId: Long,
    val fileName: String,
    var file: File,
    val caption: String?,
)
