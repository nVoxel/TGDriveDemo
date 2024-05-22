package com.voxeldev.tgdrive.database.favorites

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


/**
 * @author nvoxel
 */
@Dao
interface FavoritesDao {

    @Query("SELECT * FROM favorites")
    fun getAllFavorites(): Flow<List<FavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favoriteEntity: FavoriteEntity) : Long

    @Query("DELETE FROM favorites WHERE id = :id")
    suspend fun deleteSingleFavorite(id: Int)

    @Query("DELETE FROM favorites WHERE chat_id = :chatId")
    suspend fun deleteFolderFavorites(chatId: Long)

    @Query("DELETE FROM favorites")
    suspend fun clearTable()
}
