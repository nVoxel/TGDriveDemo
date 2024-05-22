package com.voxeldev.tgdrive

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.voxeldev.tgdrive.content.root.RootContent
import com.voxeldev.tgdrive.root.integration.DefaultRootComponent
import com.voxeldev.tgdrive.utils.extensions.getFileViewer
import com.voxeldev.tgdrive.utils.extensions.getLinkHandler
import com.voxeldev.tgdrive.utils.extensions.lazyUnsafe
import com.voxeldev.tgdrive.utils.extensions.openFilePicker
import com.voxeldev.tgdrive.utils.extensions.registerFilePicker
import com.voxeldev.tgdrive.utils.platform.CallbackFileProvider
import kotlinx.coroutines.Dispatchers

class MainActivity : ComponentActivity() {

    private lateinit var filePickerLauncher: ActivityResultLauncher<Intent>

    private val fileProvider by lazyUnsafe {
        CallbackFileProvider(
            onProvideRequest = { openFilePicker(launcher = filePickerLauncher) }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        filePickerLauncher = registerFilePicker(onFilePicked = ::onFilePicked)

        super.onCreate(savedInstanceState)

        val root = DefaultRootComponent(
            componentContext = defaultComponentContext(),
            storeFactory = DefaultStoreFactory(),
            fileProvider = fileProvider,
            fileViewer = getFileViewer(),
            linkHandler = getLinkHandler(),
            mainContext = Dispatchers.Main,
        )

        setContent {
            RootContent(component = root)
        }
    }

    private fun onFilePicked(activityResult: ActivityResult) {
        val selectedFileUri = activityResult.data?.data ?: return
        fileProvider.onProvideResponse(selectedFileUri.toString())
    }
}
