package com.voxeldev.tgdrive

import androidx.compose.runtime.Composable

/**
 * @author nvoxel
 */
enum class Orientation {
    PORTRAIT, LANDSCAPE;
}

@Composable
expect fun getOrientation(): Orientation
