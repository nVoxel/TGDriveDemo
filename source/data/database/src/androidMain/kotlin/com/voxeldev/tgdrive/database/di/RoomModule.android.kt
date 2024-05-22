package com.voxeldev.tgdrive.database.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.voxeldev.tgdrive.database.DB_FILE_NAME
import com.voxeldev.tgdrive.database.TGDriveDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * @author nvoxel
 */
actual val roomModule = module {

    single<RoomDatabase.Builder<TGDriveDatabase>> {
        val appContext = androidContext()
        val dbFile = appContext.getDatabasePath(DB_FILE_NAME)
        Room.databaseBuilder<TGDriveDatabase>(
            context = appContext,
            name = dbFile.absolutePath,
        )
    }
}
