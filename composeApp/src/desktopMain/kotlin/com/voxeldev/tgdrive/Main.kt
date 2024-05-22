package com.voxeldev.tgdrive

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.voxeldev.tgdrive.content.root.RootContent
import com.voxeldev.tgdrive.database.di.databaseModule
import com.voxeldev.tgdrive.files.api.platform.FileViewer
import com.voxeldev.tgdrive.files.api.platform.LinkHandler
import com.voxeldev.tgdrive.network.di.desktopNetworkModule
import com.voxeldev.tgdrive.root.integration.DefaultRootComponent
import com.voxeldev.tgdrive.utils.platform.CallbackFileProvider
import kotlinx.coroutines.Dispatchers
import org.koin.compose.KoinApplication
import org.koin.core.KoinApplication
import org.koin.core.logger.Level
import org.koin.core.logger.Logger
import org.koin.core.logger.MESSAGE
import java.awt.Desktop
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.io.File

fun main() {
    val lifecycle = LifecycleRegistry()

    application {
        val state = rememberWindowState()

        // Desktop

        var fileDialogOpened by remember { mutableStateOf(false) }
        val fileProvider = CallbackFileProvider(
            onProvideRequest = {
                fileDialogOpened = true
            }
        )

        val fileViewer = FileViewer { file ->
            if (Desktop.isDesktopSupported()) {
                runCatching { Desktop.getDesktop().open(File(file.local.path)) }
                    .onFailure { error -> error.printStackTrace() }
            }
        }

        KoinApplication(application = { initKoin() }) {
            val root = runOnUiThread {
                DefaultRootComponent(
                    componentContext = DefaultComponentContext(
                        lifecycle = lifecycle
                    ),
                    fileProvider = fileProvider,
                    fileViewer = fileViewer,
                    linkHandler = null,
                    storeFactory = DefaultStoreFactory(),
                    mainContext = Dispatchers.Main,
                )
            }

            Window(
                state = state,
                onCloseRequest = ::exitApplication,
                title = "TGDrive",
            ) {
                if (fileDialogOpened) {
                    FileDialog { path ->
                        fileDialogOpened = false
                        path?.let { fileProvider.onProvideResponse(it) }
                    }
                }

                RootContent(component = root)
            }
        }
    }
}

fun KoinApplication.initKoin() {
    logger(object : Logger() {
        override fun display(level: Level, msg: MESSAGE) {
            println("[$level]: $msg")
        }
    })
    modules(desktopNetworkModule, databaseModule) // TODO
}
