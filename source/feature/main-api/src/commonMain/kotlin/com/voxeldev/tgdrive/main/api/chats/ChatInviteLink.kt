package com.voxeldev.tgdrive.main.api.chats

import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

/**
 * @author nvoxel
 */
@OptIn(ExperimentalObjCName::class)
@ObjCName("CommonChatInviteLink")
data class ChatInviteLink(
    val link: String,
    val name: String,
    val creatorUserId: Long,
    val expirationDate: Int,
    val isPrimary: Boolean,
    val isRevoked: Boolean,
)
