package com.voxeldev.tgdrive.database.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.voxeldev.tgdrive.database.DB_FILE_NAME
import com.voxeldev.tgdrive.database.TGDriveDatabase
import org.koin.dsl.module
import java.io.File

/**
 * @author nvoxel
 */
actual val roomModule = module {

    single<RoomDatabase.Builder<TGDriveDatabase>> {
        val dbFile = File(System.getProperty("java.io.tmpdir"), DB_FILE_NAME)
        Room.databaseBuilder(name = dbFile.absolutePath)
    }
}
