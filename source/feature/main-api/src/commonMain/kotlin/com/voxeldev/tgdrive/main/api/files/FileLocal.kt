package com.voxeldev.tgdrive.main.api.files

import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

/**
 * @author nvoxel
 */
@OptIn(ExperimentalObjCName::class)
@ObjCName(name = "CommonFileLocal")
data class FileLocal(
    val path: String,
    val canBeDownloaded: Boolean,
    val canBeDeleted: Boolean,
    val isDownloadingActive: Boolean,
    val isDownloadingCompleted: Boolean,
    val downloadOffset: Long,
    val downloadedPrefixSize: Long,
    val downloadedSize: Long,
)
