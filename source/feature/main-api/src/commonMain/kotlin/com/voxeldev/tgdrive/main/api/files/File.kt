package com.voxeldev.tgdrive.main.api.files

import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

/**
 * @author nvoxel
 */
@OptIn(ExperimentalObjCName::class)
@ObjCName(name = "CommonFile")
data class File(
    val id: Int,
    val size: Long,
    val expectedSize: Long,
    val local: FileLocal,
    val remote: FileRemote,
) {

    fun isDownloaded() = local.downloadedSize == expectedSize
}
