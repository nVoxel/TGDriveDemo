package com.voxeldev.tgdrive.main.api.messages

import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

/**
 * @author nvoxel
 */
@OptIn(ExperimentalObjCName::class)
@ObjCName(name = "CommonMessage")
data class Message(
    val id: Long,
    val messageSender: MessageSender?,
    val chatId: Long,
    val isOutgoing: Boolean,
    val isPinned: Boolean,
    val canBeEdited: Boolean,
    val canBeForwarded: Boolean,
    val canBeSaved: Boolean,
    val canBeDeletedOnlyForSelf: Boolean,
    val canBeDeletedForAllUsers: Boolean,
    val canGetMessageThread: Boolean,
    val canGetViewers: Boolean,
    val isChannelPost: Boolean,
    val containsUnreadMention: Boolean,
    val date: Int,
    val editDate: Int,
    val content: MessageContent?,
)
