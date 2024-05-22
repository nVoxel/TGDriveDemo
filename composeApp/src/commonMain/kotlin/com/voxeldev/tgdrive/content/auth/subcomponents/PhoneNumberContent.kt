package com.voxeldev.tgdrive.content.auth.subcomponents

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.voxeldev.tgdrive.auth.subcomponents.phonenumber.PhoneNumberComponent
import com.voxeldev.tgdrive.components.Form

/**
 * @author nvoxel
 */
@Composable
internal fun PhoneNumberContent(component: PhoneNumberComponent) {
    with(component) {
        val model by model.subscribeAsState()

        Form(
            formTitle = "Phone Number",
            isLoading = model.isLoading,
            errorText = model.errorText,
            retryCallback = ::onPhoneReset,
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = model.phoneNumber,
                onValueChange = ::onPhoneUpdate,
                label = { Text(text = "Enter phone number") },
            )

            Spacer(modifier = Modifier.height(height = 12.dp))

            Button(onClick = ::onContinueClicked) {
                Text(text = "Continue")
            }
        }
    }
}
