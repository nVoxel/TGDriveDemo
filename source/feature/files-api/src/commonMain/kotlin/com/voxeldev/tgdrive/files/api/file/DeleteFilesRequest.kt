package com.voxeldev.tgdrive.files.api.file

import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

/**
 * @author nvoxel
 */
@OptIn(ExperimentalObjCName::class)
@ObjCName(name = "CommonDeleteFilesRequest")
data class DeleteFilesRequest(
    val chatId: Long,
    val messageIds: List<Long>,
)
