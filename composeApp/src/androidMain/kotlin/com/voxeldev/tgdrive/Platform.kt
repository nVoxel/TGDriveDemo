package com.voxeldev.tgdrive

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

/**
 * @author nvoxel
 */
@Composable
actual fun getOrientation(): Orientation =
    when (LocalConfiguration.current.orientation) {
        Configuration.ORIENTATION_PORTRAIT -> Orientation.PORTRAIT
        else -> Orientation.LANDSCAPE
    }
