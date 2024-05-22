package com.voxeldev.tgdrive.main.api.chats

import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

/**
 * @author nvoxel
 */
@OptIn(ExperimentalObjCName::class)
@ObjCName(name = "CommonChatType")
sealed interface ChatType {
    data class Private(val userId: Long) : ChatType
    data class BasicGroup(val basicGroupId: Long) : ChatType
    data class Supergroup(val supergroupId: Long, val isChannel: Boolean) : ChatType
    data class Secret(val secretChatId: Long, val userId: Long) : ChatType
}
