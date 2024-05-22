package com.voxeldev.tgdrive.main.api.messages

import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

/**
 * @author nvoxel
 */
@OptIn(ExperimentalObjCName::class)
@ObjCName(name = "CommonMessageSender")
sealed interface MessageSender {

    data class User(val userId: Long) : MessageSender

    data class Chat(val chatId: Long) : MessageSender
}
