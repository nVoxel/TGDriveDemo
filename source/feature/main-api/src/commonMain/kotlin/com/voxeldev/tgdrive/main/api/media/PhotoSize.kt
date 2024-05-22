package com.voxeldev.tgdrive.main.api.media

import com.voxeldev.tgdrive.main.api.files.File
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

/**
 * @author nvoxel
 */
@OptIn(ExperimentalObjCName::class)
@ObjCName(name = "CommonPhotoSize")
data class PhotoSize(
    val type: String,
    val photo: File,
    val width: Int,
    val height: Int,
)
