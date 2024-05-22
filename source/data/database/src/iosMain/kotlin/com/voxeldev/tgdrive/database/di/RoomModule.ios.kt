package com.voxeldev.tgdrive.database.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.voxeldev.tgdrive.database.DB_FILE_NAME
import com.voxeldev.tgdrive.database.TGDriveDatabase
import org.koin.dsl.module
import platform.Foundation.NSHomeDirectory

/**
 * @author nvoxel
 */
actual val roomModule = module {

    single<RoomDatabase.Builder<TGDriveDatabase>> {
        val dbFilePath = NSHomeDirectory() + "/${DB_FILE_NAME}"
        Room.databaseBuilder<TGDriveDatabase>(
            name = dbFilePath,
            factory = { instantiateImpl() },
        )
    }
}

// temporary solution of instantiateImpl not reachable from iosMain
// https://github.com/google/ksp/issues/929
expect fun instantiateImpl(): TGDriveDatabase
