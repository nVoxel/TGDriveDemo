package com.voxeldev.tgdrive.database.favorites

import com.voxeldev.tgdrive.favorites.api.FavoriteMessage
import com.voxeldev.tgdrive.favorites.api.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * @author nvoxel
 */
class DefaultFavoritesRepository(
    private val favoritesDao: FavoritesDao,
    private val favoritesMapper: FavoritesMapper,
) : FavoritesRepository {

    override suspend fun getAll(): Flow<List<FavoriteMessage>> = favoritesDao.getAllFavorites().map { favoriteList ->
        favoriteList.map { favoriteEntity -> favoritesMapper.toResponse(favoriteEntity = favoriteEntity) }
    }

    override suspend fun insert(favoriteMessage: FavoriteMessage) = favoritesDao.insertFavorite(
        favoriteEntity = favoritesMapper.toRequest(favoriteMessage = favoriteMessage)
    )

    override suspend fun deleteSingle(id: Int) = favoritesDao.deleteSingleFavorite(id = id)

    override suspend fun deleteByFolder(chatId: Long) = favoritesDao.deleteFolderFavorites(chatId = chatId)

    override suspend fun clearTable() = favoritesDao.clearTable()
}
