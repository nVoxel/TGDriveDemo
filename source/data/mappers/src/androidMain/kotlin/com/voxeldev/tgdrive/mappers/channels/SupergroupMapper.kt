package com.voxeldev.tgdrive.mappers.channels

import com.voxeldev.tgdrive.files.api.channels.SupergroupFullInfo as CommonSupergroupFullInfo
import com.voxeldev.tgdrive.main.api.chats.ChatInviteLink as CommonChatInviteLink
import com.voxeldev.tgdrive.main.api.chats.ChatPhoto as CommonChatPhoto
import com.voxeldev.tgdrive.mappers.media.PhotoMapper as CommonPhotoMapper
import org.drinkless.td.libcore.telegram.TdApi
import com.voxeldev.tgdrive.files.api.channels.Supergroup as CommonSupergroup

/**
 * @author nvoxel
 */
class SupergroupMapper(
    private val photoMapper: CommonPhotoMapper,
) {

    fun toResponse(supergroup: TdApi.Supergroup): CommonSupergroup =
        CommonSupergroup(
            supergroupId = supergroup.id,
            username = supergroup.username,
            date = supergroup.date,
            isCreator = supergroup.status is TdApi.ChatMemberStatusCreator,
            memberCount = supergroup.memberCount,
            isChannel = supergroup.isChannel,
        )

    fun toResponse(supergroupFullInfo: TdApi.SupergroupFullInfo): CommonSupergroupFullInfo =
        CommonSupergroupFullInfo(
            chatPhoto = supergroupFullInfo.photo?.toResponse(),
            description = supergroupFullInfo.description,
            chatInviteLink = supergroupFullInfo.inviteLink?.toResponse(),
        )

    private fun TdApi.ChatPhoto.toResponse(): CommonChatPhoto =
        CommonChatPhoto(
            id = id,
            addedDate = addedDate,
            size = photoMapper.toResponse(sizes = sizes),
        )

    private fun TdApi.ChatInviteLink.toResponse(): CommonChatInviteLink =
        CommonChatInviteLink(
            link = inviteLink,
            name = name,
            creatorUserId = creatorUserId,
            expirationDate = expirationDate,
            isPrimary = isPrimary,
            isRevoked = isRevoked,
        )
}
