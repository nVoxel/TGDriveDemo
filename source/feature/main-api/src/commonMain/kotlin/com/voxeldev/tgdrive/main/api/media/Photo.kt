package com.voxeldev.tgdrive.main.api.media

import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

/**
 * @author nvoxel
 */
@OptIn(ExperimentalObjCName::class)
@ObjCName(name = "CommonPhoto")
data class Photo(val sizes: List<PhotoSize>)
