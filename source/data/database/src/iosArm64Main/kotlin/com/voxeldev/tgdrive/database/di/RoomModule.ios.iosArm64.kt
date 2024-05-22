package com.voxeldev.tgdrive.database.di

import com.voxeldev.tgdrive.database.TGDriveDatabase
import com.voxeldev.tgdrive.database.instantiateImpl

// iosX64 target
actual fun instantiateImpl(): TGDriveDatabase = TGDriveDatabase::class.instantiateImpl()


