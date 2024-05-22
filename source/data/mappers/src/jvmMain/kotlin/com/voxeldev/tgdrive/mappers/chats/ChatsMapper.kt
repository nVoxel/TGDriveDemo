package com.voxeldev.tgdrive.mappers.chats

import com.voxeldev.tgdrive.mappers.media.FileMapper
import org.drinkless.tdlib.TdApi.Chat
import org.drinkless.tdlib.TdApi.ChatPhotoInfo
import org.drinkless.tdlib.TdApi.ChatType
import org.drinkless.tdlib.TdApi.ChatTypeBasicGroup
import org.drinkless.tdlib.TdApi.ChatTypePrivate
import org.drinkless.tdlib.TdApi.ChatTypeSecret
import org.drinkless.tdlib.TdApi.ChatTypeSupergroup
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
