package com.voxeldev.tgdrive.files.api.folder

import com.voxeldev.tgdrive.files.api.channels.Supergroup
import com.voxeldev.tgdrive.main.api.chats.ChatInviteLink
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

/**
 * @author nvoxel
 */
@OptIn(ExperimentalObjCName::class)
@ObjCName(name = "CommonFolder")
data class Folder(
    val supergroup: Supergroup,
    val chatId: Long,
    val title: String,
    val description: String,
    val fileCount: Int,
    val inviteLink: ChatInviteLink?,
)
