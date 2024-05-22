package com.voxeldev.tgdrive.main.api.media

import com.voxeldev.tgdrive.main.api.files.File
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

/**
 * @author nvoxel
 */
@OptIn(ExperimentalObjCName::class)
@ObjCName(name = "CommonDocument")
data class Document(
    val fileName: String,
    val mimeType: String,
    val thumbnail: Thumbnail?,
    val file: File,
)
