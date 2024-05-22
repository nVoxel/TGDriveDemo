package com.voxeldev.tgdrive.main.api.chats

import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

/**
 * @author nvoxel
 */
@OptIn(ExperimentalObjCName::class)
@ObjCName(name = "CommonChat")
data class Chat(
    val id: Long,
    val type: ChatType?,
    val title: String,
    val photo: ChatPhotoInfo?,
    val hasProtectedContent: Boolean,
)
