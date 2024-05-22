package com.voxeldev.tgdrive.favorites.api

import kotlinx.coroutines.flow.Flow

/**
 * @author nvoxel
 */
interface FavoritesRepository {

    suspend fun getAll(): Flow<List<FavoriteMessage>>

    suspend fun insert(favoriteMessage: FavoriteMessage) : Long

    suspend fun deleteSingle(id: Int)

    suspend fun deleteByFolder(chatId: Long)

    suspend fun clearTable()
}
