package com.voxeldev.tgdrive.database.di

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.voxeldev.tgdrive.database.TGDriveDatabase
import com.voxeldev.tgdrive.database.favorites.DefaultFavoritesRepository
import com.voxeldev.tgdrive.database.favorites.FavoritesMapper
import com.voxeldev.tgdrive.favorites.api.FavoritesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.dsl.module

/**
 * @author nvoxel
 */
val databaseModule = module {
    includes(roomModule)

    single<TGDriveDatabase> {
        val builder: RoomDatabase.Builder<TGDriveDatabase> = get()
        builder
            .setDriver(driver = BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }

    single { FavoritesMapper() }

    single<FavoritesRepository> {
        val tgDriveDatabase: TGDriveDatabase = get()
        DefaultFavoritesRepository(
            favoritesDao = tgDriveDatabase.favoritesDao(),
            favoritesMapper = get(),
        )
    }
}
