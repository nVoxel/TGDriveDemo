package com.voxeldev.tgdrive.auth.api

import kotlin.native.ObjCName

/**
 * @author nvoxel
 */
@ObjCName(name = "CommonAuthenticationCodeType")
enum class AuthenticationCodeType {
    TELEGRAM_MESSAGE, SMS, CALL;
}
