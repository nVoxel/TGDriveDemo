package com.voxeldev.tgdrive.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

/**
 * @author nvoxel
 */
@Composable
internal fun RoundIcon(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    contentDescription: String? = null,
) {
    Icon(
        modifier = modifier
            .size(size = 40.dp)
            .background(color = MaterialTheme.colorScheme.primary, shape = CircleShape)
            .padding(all = 8.dp),
        imageVector = imageVector,
        contentDescription = contentDescription,
        tint = if (isSystemInDarkTheme()) Color.Black else Color.White,
    )
}
