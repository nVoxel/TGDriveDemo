package com.voxeldev.tgdrive.files.api.file

import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

/**
 * @author nvoxel
 */
@OptIn(ExperimentalObjCName::class)
@ObjCName(name = "CommonLocalFile")
data class LocalFile(
    val id: Int? = null,
    val name: String,
    val path: String,
    val uploadProgress: Float? = null,
)
