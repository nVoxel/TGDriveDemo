package com.voxeldev.tgdrive.favorites.api

/**
 * @author nvoxel
 */
data class GetMessageRequest(
    val chatId: Long,
    val messageId: Long,
)
