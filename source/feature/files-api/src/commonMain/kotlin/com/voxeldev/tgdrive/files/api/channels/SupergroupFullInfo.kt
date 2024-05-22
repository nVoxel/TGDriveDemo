package com.voxeldev.tgdrive.files.api.channels

import com.voxeldev.tgdrive.main.api.chats.ChatInviteLink
import com.voxeldev.tgdrive.main.api.chats.ChatPhoto
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

/**
 * @author nvoxel
 */
@OptIn(ExperimentalObjCName::class)
@ObjCName("CommonSupergroupFullInfo")
data class SupergroupFullInfo(
    val chatPhoto: ChatPhoto?,
    val description: String,
    val chatInviteLink: ChatInviteLink?,
)
