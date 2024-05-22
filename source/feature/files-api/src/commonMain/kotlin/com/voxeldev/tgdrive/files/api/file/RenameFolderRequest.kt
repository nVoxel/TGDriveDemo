package com.voxeldev.tgdrive.files.api.file

import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

/**
 * @author nvoxel
 */
@OptIn(ExperimentalObjCName::class)
@ObjCName(name = "CommonRenameFolderRequest")
data class RenameFolderRequest(
    val chatId: Long,
    val title: String,
)
