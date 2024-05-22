package com.voxeldev.tgdrive.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

/**
 * @author nvoxel
 */
@Composable
internal fun TextFieldDialog(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    text: String,
    textLabel: @Composable () -> Unit,
    onTextChange: (String) -> Unit,
    confirmButtonContent: @Composable RowScope.() -> Unit,
    onConfirmClicked: () -> Unit,
) {
    if (isVisible) {
        Dialog(
            onDismissRequest = onDismiss,
        ) {
            ElevatedCard {
                Column(
                    modifier = Modifier
                        .padding(all = 16.dp)
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = text,
                        onValueChange = onTextChange,
                        label = textLabel,
                    )

                    Spacer(modifier = Modifier.height(height = 8.dp))

                    Button(
                        onClick = onConfirmClicked,
                        content = confirmButtonContent
                    )
                }
            }
        }
    }
}
