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
import com.voxeldev.tgdrive.auth.subcomponents.code.CodeComponent
import com.voxeldev.tgdrive.components.Form

/**
 * @author nvoxel
 */
@Composable
internal fun CodeContent(component: CodeComponent) {
    with(component) {
        val model by model.subscribeAsState()

        Form(
            formTitle = "Auth Code",
            isLoading = model.isLoading,
            errorText = model.errorText,
            retryCallback = ::onCodeReset,
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = model.code,
                onValueChange = ::onCodeUpdate,
                label = { Text(text = "Enter auth code") }
            )

            Spacer(modifier = Modifier.height(height = 12.dp))

            Button(onClick = ::onContinueClicked) {
                Text(text = "Continue")
            }
        }
    }
}
