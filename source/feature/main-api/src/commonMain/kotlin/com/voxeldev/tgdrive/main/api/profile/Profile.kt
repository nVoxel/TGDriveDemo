package com.voxeldev.tgdrive.main.api.profile

import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

/**
 * @author nvoxel
 */
@OptIn(ExperimentalObjCName::class)
@ObjCName(name = "CommonProfile")
data class Profile(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val username: String,
    val phoneNumber: String,
    val profilePhoto: ProfilePhoto?,
    val isContact: Boolean,
    val isMutualContact: Boolean,
    val isVerified: Boolean,
    val isSupport: Boolean,
    val restrictionReason: String,
    val isScam: Boolean,
    val isFake: Boolean,
    val haveAccess: Boolean,
    val languageCode: String,
)
