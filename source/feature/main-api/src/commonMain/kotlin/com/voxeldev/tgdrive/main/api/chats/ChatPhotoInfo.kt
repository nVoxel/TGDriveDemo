package com.voxeldev.tgdrive.main.api.chats

import com.voxeldev.tgdrive.main.api.files.File
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

/**
 * @author nvoxel
 */
@OptIn(ExperimentalObjCName::class)
@ObjCName(name = "CommonChatPhotoInfo")
data class ChatPhotoInfo(
    val small: File,
    val big: File,
)
