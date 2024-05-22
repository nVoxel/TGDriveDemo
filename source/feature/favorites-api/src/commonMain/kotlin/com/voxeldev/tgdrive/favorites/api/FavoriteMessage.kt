package com.voxeldev.tgdrive.favorites.api

import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

/**
 * @author nvoxel
 */
@OptIn(ExperimentalObjCName::class)
@ObjCName(name = "CommonFavoriteMessage")
data class FavoriteMessage(
    val id: Int = 0,
    val chatId: Long,
    val messageId: Long,
)
