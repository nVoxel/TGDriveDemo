package com.voxeldev.tgdrive.mappers.user

import com.voxeldev.tgdrive.main.api.profile.Profile
import com.voxeldev.tgdrive.mappers.media.FileMapper
import org.drinkless.tdlib.TdApi.ProfilePhoto
import org.drinkless.tdlib.TdApi.User
import com.voxeldev.tgdrive.main.api.profile.ProfilePhoto as CommonProfilePhoto

/**
 * @author nvoxel
 */
class UserMapper(
    private val fileMapper: FileMapper,
) {

    fun toResponse(user: User): Profile =
        Profile(
            id = user.id,
            firstName = user.firstName,
            lastName = user.lastName,
            username = user.usernames.editableUsername,
            phoneNumber = user.phoneNumber,
            profilePhoto = user.profilePhoto?.let { user.profilePhoto.toResponse() },
            isContact = user.isContact,
            isMutualContact = user.isMutualContact,
            isVerified = user.isVerified,
            isSupport = user.isSupport,
            restrictionReason = user.restrictionReason,
            isScam = user.isScam,
            isFake = user.isFake,
            haveAccess = user.haveAccess,
            languageCode = user.languageCode,
        )

    private fun ProfilePhoto.toResponse(): CommonProfilePhoto =
        CommonProfilePhoto(
            id = id,
            big = fileMapper.toResponse(big),
            small = fileMapper.toResponse(small),
        )
}
