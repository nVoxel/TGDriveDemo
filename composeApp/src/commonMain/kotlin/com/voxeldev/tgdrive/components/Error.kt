package com.voxeldev.tgdrive.components

/**
 * @author nvoxel
 */
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.voxeldev.tgdrive.theme.AdditionalIcons

/**
 * @author nvoxel
 */
@Composable
internal fun Error(
    message: String,
    shouldShowRetry: Boolean,
    retryCallback: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "Error: $message",
        )
        if (shouldShowRetry) {
            Button(
                modifier = Modifier.padding(top = 16.dp),
                onClick = { retryCallback() },
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    imageVector = AdditionalIcons.RestartAlt,
                    contentDescription = null,
                )
                Text(
                    modifier = Modifier.padding(start = 4.dp, end = 2.dp),
                    text = "Retry",
                )
            }
        }
    }
}
