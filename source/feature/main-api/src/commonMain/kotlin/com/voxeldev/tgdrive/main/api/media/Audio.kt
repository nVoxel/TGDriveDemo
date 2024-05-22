package com.voxeldev.tgdrive.main.api.media

import com.voxeldev.tgdrive.main.api.files.File
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

/**
 * @author nvoxel
 */
@OptIn(ExperimentalObjCName::class)
@ObjCName(name = "CommonAudio")
data class Audio(
    val duration: Int,
    val title: String,
    val performer: String,
    val fileName: String,
    val mimeType: String,
    val file: File,
)
