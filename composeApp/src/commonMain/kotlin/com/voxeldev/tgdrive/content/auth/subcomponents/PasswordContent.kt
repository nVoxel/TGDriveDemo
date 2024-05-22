package com.voxeldev.tgdrive.content.auth.subcomponents

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.voxeldev.tgdrive.auth.subcomponents.password.PasswordComponent
import com.voxeldev.tgdrive.components.Form
import com.voxeldev.tgdrive.theme.AdditionalIcons

/**
 * @author nvoxel
 */
@Composable
internal fun PasswordContent(component: PasswordComponent) {
    with(component) {
        val model by model.subscribeAsState()

        Form(
            formTitle = "Password",
            isLoading = model.isLoading,
            errorText = model.errorText,
            retryCallback = ::onPasswordReset,
        ) {
            var passwordVisible by rememberSaveable { mutableStateOf(false) }

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = model.password,
                onValueChange = ::onPasswordUpdate,
                placeholder = { Text(text = "Enter password") },
                singleLine = true,
                isError = model.errorText != null,
                visualTransformation =
                if (passwordVisible) VisualTransformation.None
                else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val image = if (passwordVisible)
                        AdditionalIcons.VisibilityOff
                    else AdditionalIcons.Visibility

                    val description =
                        if (passwordVisible) "Hide password"
                        else "Show password"

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, description)
                    }
                }
            )

            Spacer(modifier = Modifier.height(height = 12.dp))

            Button(onClick = ::onContinueClicked) {
                Text(text = "Continue")
            }
        }
    }
}
