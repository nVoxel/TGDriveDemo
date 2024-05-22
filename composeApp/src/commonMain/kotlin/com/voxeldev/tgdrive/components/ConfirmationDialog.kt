package com.voxeldev.tgdrive.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

/**
 * @author nvoxel
 */
@Composable
internal fun ConfirmationDialog(
    isVisible: Boolean,
    title: @Composable () -> Unit,
    text: @Composable () -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    if (isVisible) {
        AlertDialog(
            title = title,
            text = text,
            onDismissRequest = onDismiss,
            confirmButton = {
                Button(
                    onClick = onConfirm,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) { Text(text = "Yes") }
            },
            dismissButton = {
                Button(
                    onClick = onDismiss,
                ) { Text(text = "No") }
            },
        )
    }
}
