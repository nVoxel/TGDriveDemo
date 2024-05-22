package com.voxeldev.tgdrive.database.favorites

import com.voxeldev.tgdrive.favorites.api.FavoriteMessage

/**
 * @author nvoxel
 */
class FavoritesMapper {

    fun toResponse(favoriteEntity: FavoriteEntity) : FavoriteMessage =
        FavoriteMessage(
            id = favoriteEntity.id,
            chatId = favoriteEntity.chatId,
            messageId = favoriteEntity.messageId,
        )

    fun toRequest(favoriteMessage: FavoriteMessage) : FavoriteEntity =
        FavoriteEntity(
            id = favoriteMessage.id,
            chatId = favoriteMessage.chatId,
            messageId = favoriteMessage.messageId,
        )
}
