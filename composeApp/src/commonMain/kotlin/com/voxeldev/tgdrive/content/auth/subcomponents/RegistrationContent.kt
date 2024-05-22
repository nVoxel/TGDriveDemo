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
import com.voxeldev.tgdrive.auth.subcomponents.registration.RegistrationComponent
import com.voxeldev.tgdrive.components.Form

/**
 * @author nvoxel
 */
@Composable
internal fun RegistrationContent(component: RegistrationComponent) {
    with(component) {
        val model by model.subscribeAsState()

        Form(
            formTitle = "Registration",
            isLoading = model.isLoading,
            errorText = model.errorText,
            retryCallback = ::onFieldsReset,
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = model.firstName,
                onValueChange = ::onFirstNameUpdate,
                label = { Text(text = "Enter your first name") }
            )

            Spacer(modifier = Modifier.height(height = 6.dp))

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = model.lastName,
                onValueChange = ::onLastNameUpdate,
                label = { Text(text = "Enter your last name") }
            )

            Spacer(modifier = Modifier.height(height = 12.dp))

            Button(onClick = ::onContinueClicked) {
                Text(text = "Continue")
            }
        }
    }
}
