package com.voxeldev.tgdrive.mappers.channels

import com.voxeldev.tgdrive.mappers.media.PhotoMapper
import org.drinkless.tdlib.TdApi.ChatInviteLink
import org.drinkless.tdlib.TdApi.ChatMemberStatusCreator
import org.drinkless.tdlib.TdApi.ChatPhoto
import org.drinkless.tdlib.TdApi.Supergroup
import org.drinkless.tdlib.TdApi.SupergroupFullInfo
import com.voxeldev.tgdrive.files.api.channels.Supergroup as CommonSupergroup
import com.voxeldev.tgdrive.files.api.channels.SupergroupFullInfo as CommonSupergroupFullInfo
import com.voxeldev.tgdrive.main.api.chats.ChatInviteLink as CommonChatInviteLink
import com.voxeldev.tgdrive.main.api.chats.ChatPhoto as CommonChatPhoto

/**
 * @author nvoxel
 */
class SupergroupMapper(
    private val photoMapper: PhotoMapper,
) {

    fun toResponse(supergroup: Supergroup): CommonSupergroup =
        CommonSupergroup(
            supergroupId = supergroup.id,
            username = supergroup.usernames?.editableUsername,
            date = supergroup.date,
            isCreator = supergroup.status is ChatMemberStatusCreator,
            memberCount = supergroup.memberCount,
            isChannel = supergroup.isChannel,
        )

    fun toResponse(supergroupFullInfo: SupergroupFullInfo): CommonSupergroupFullInfo =
        CommonSupergroupFullInfo(
            chatPhoto = supergroupFullInfo.photo?.toResponse(),
            description = supergroupFullInfo.description,
            chatInviteLink = supergroupFullInfo.inviteLink?.toResponse(),
        )

    private fun ChatPhoto.toResponse(): CommonChatPhoto =
        CommonChatPhoto(
            id = id,
            addedDate = addedDate,
            size = photoMapper.toResponse(sizes = sizes),
        )

    private fun ChatInviteLink.toResponse(): CommonChatInviteLink =
        CommonChatInviteLink(
            link = inviteLink,
            name = name,
            creatorUserId = creatorUserId,
            expirationDate = expirationDate,
            isPrimary = isPrimary,
            isRevoked = isRevoked,
        )
}
