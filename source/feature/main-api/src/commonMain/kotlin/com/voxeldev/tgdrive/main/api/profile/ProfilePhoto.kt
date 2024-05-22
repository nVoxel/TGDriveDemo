package com.voxeldev.tgdrive.main.api.profile

import com.voxeldev.tgdrive.main.api.files.File
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

/**
 * @author nvoxel
 */
@OptIn(ExperimentalObjCName::class)
@ObjCName(name = "CommonProfilePhoto")
data class ProfilePhoto(
    val id: Long,
    val small: File,
    val big: File,
)
