package com.voxeldev.tgdrive.mappers.chats

import com.voxeldev.tgdrive.mappers.media.FileMapper
import org.drinkless.td.libcore.telegram.TdApi.Chat
import org.drinkless.td.libcore.telegram.TdApi.ChatPhotoInfo
import org.drinkless.td.libcore.telegram.TdApi.ChatType
import org.drinkless.td.libcore.telegram.TdApi.ChatTypeBasicGroup
import org.drinkless.td.libcore.telegram.TdApi.ChatTypePrivate
import org.drinkless.td.libcore.telegram.TdApi.ChatTypeSecret
import org.drinkless.td.libcore.telegram.TdApi.ChatTypeSupergroup
import com.voxeldev.tgdrive.main.api.chats.Chat as CommonChat
import com.voxeldev.tgdrive.main.api.chats.ChatPhotoInfo as CommonChatPhotoInfo
import com.voxeldev.tgdrive.main.api.chats.ChatType as CommonChatType

/**
 * @author nvoxel
 */
class ChatsMapper(
    private val fileMapper: FileMapper,
) {

    fun toResponse(chat: Chat): CommonChat =
        CommonChat(
            id = chat.id,
            type = chat.type.toResponse(),
            title = chat.title,
            photo = chat.photo?.let { chat.photo.toResponse() },
            hasProtectedContent = chat.hasProtectedContent,
        )

    private fun ChatType.toResponse(): CommonChatType? =
        when (this) {
            is ChatTypePrivate -> CommonChatType.Private(userId = userId)
            is ChatTypeBasicGroup -> CommonChatType.BasicGroup(basicGroupId = basicGroupId)
            is ChatTypeSupergroup -> CommonChatType.Supergroup(supergroupId = supergroupId, isChannel = isChannel)
            is ChatTypeSecret -> CommonChatType.Secret(secretChatId = secretChatId.toLong(), userId = userId)
            else -> null
        }

    private fun ChatPhotoInfo.toResponse(): CommonChatPhotoInfo =
        CommonChatPhotoInfo(
            small = fileMapper.toResponse(small),
            big = fileMapper.toResponse(big),
        )
}
