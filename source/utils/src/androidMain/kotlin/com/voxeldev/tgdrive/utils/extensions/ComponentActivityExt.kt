package com.voxeldev.tgdrive.utils.extensions

import android.app.Activity
import android.content.Intent
import android.os.Environment
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ShareCompat
import androidx.core.content.FileProvider
import com.voxeldev.tgdrive.files.api.platform.FileViewer
import com.voxeldev.tgdrive.files.api.platform.LinkHandler
import java.io.File

private const val PROVIDER_AUTHORITY = "com.voxeldev.tgdrive.provider"

/**
 * @author nvoxel
 */
fun ComponentActivity.registerFilePicker(
    onFilePicked: (ActivityResult) -> Unit,
): ActivityResultLauncher<Intent> =
    registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            onFilePicked(result)
        }
    }

fun ComponentActivity.openFilePicker(launcher: ActivityResultLauncher<Intent>) {
    val intent = Intent(Intent.ACTION_GET_CONTENT)
    intent.type = "*/*"
    launcher.launch(intent)
}

fun ComponentActivity.getFileViewer(): FileViewer = FileViewer { file ->
    println(Environment.getExternalStorageDirectory().toString())
    val intent = Intent(Intent.ACTION_VIEW)
    val uri = FileProvider.getUriForFile(applicationContext, PROVIDER_AUTHORITY, File(file.local.path))
    intent.setData(uri)
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    startActivity(intent)
}

fun ComponentActivity.getLinkHandler(): LinkHandler = LinkHandler { link ->
    ShareCompat.IntentBuilder(this)
        .setType("text/plain")
        .setChooserTitle("Share folder")
        .setText(link)
        .startChooser()
}
