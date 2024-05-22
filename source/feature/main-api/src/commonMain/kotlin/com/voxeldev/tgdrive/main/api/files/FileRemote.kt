package com.voxeldev.tgdrive.main.api.files

import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

/**
 * @author nvoxel
 */
@OptIn(ExperimentalObjCName::class)
@ObjCName(name = "CommonFileRemote")
data class FileRemote(
    var id: String,
    var uniqueId: String,
    var isUploadingActive: Boolean,
    var isUploadingCompleted: Boolean,
    var uploadedSize: Long,
)
