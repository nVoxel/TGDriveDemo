package com.voxeldev.tgdrive.files.api.channels

import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

/**
 * @author nvoxel
 */
@OptIn(ExperimentalObjCName::class)
@ObjCName("CommonSupergroup")
data class Supergroup(
    val supergroupId: Long,
    val username: String?,
    val date: Int,
    val isCreator: Boolean,
    val memberCount: Int,
    val isChannel: Boolean,
)
