package com.voxeldev.tgdrive

import android.app.Application
import com.voxeldev.tgdrive.database.di.databaseModule
import com.voxeldev.tgdrive.network.di.androidNetworkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * @author nvoxel
 */
internal class App : Application() {

    override fun onCreate() {
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(androidNetworkModule, databaseModule) // TODO
        }

        super.onCreate()
    }
}
