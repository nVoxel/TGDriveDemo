package com.voxeldev.tgdrive.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.voxeldev.tgdrive.database.favorites.FavoriteEntity
import com.voxeldev.tgdrive.database.favorites.FavoritesDao

/**
 * @author nvoxel
 */
@Database(entities = [FavoriteEntity::class], version = 1)
abstract class TGDriveDatabase : RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao
}

internal const val DB_FILE_NAME = "tgdrive.db"
