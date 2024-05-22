package com.voxeldev.tgdrive.content.auth.subcomponents

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.voxeldev.tgdrive.auth.subcomponents.confirmation.ConfirmationComponent
import com.voxeldev.tgdrive.components.Form

/**
 * @author nvoxel
 */
@Composable
internal fun ConfirmationContent(component: ConfirmationComponent) {
    with(component.model) {
        Form(
            formTitle = "Scan QR Code",
            isLoading = false,
            errorText = null,
            retryCallback = {},
        ) {
            Column {
                Text(text = "Confirm sign-in by scanning the QR code from the connected device")
                Text(text = confirmationLink)
            }
        }
    }
}
