package com.voxeldev.tgdrive.main.api.media

import com.voxeldev.tgdrive.main.api.files.File
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

/**
 * @author nvoxel
 */
@OptIn(ExperimentalObjCName::class)
@ObjCName(name = "CommonVideo")
data class Video(
    val duration: Int,
    val width: Int,
    val height: Int,
    val fileName: String,
    val mimeType: String,
    val supportsStreaming: Boolean,
    val thumbnail: Thumbnail?,
    val video: File,
)
