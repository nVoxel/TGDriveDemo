package com.voxeldev.tgdrive.files.api.file

import com.voxeldev.tgdrive.main.api.files.File
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

/**
 * @author nvoxel
 */
@OptIn(ExperimentalObjCName::class)
@ObjCName(name = "CommonSendFileRequest")
data class SendFileRequest(
    val chatId: Long,
    val file: File,
)
