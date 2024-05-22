package com.voxeldev.tgdrive.main.api.chats

import com.voxeldev.tgdrive.main.api.media.PhotoSize
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

/**
 * @author nvoxel
 */
@OptIn(ExperimentalObjCName::class)
@ObjCName("CommonChatPhoto")
data class ChatPhoto(
    val id: Long,
    val addedDate: Int,
    val size: List<PhotoSize>,
)
