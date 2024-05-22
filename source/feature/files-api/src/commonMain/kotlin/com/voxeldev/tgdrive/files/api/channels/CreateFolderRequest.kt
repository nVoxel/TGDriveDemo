package com.voxeldev.tgdrive.files.api.channels

import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

/**
 * @author nvoxel
 */
@OptIn(ExperimentalObjCName::class)
@ObjCName(name = "CommonCreateFolderRequest")
data class CreateFolderRequest(
    val title: String,
    val description: String,
)
