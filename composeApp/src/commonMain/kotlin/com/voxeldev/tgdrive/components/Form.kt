package com.voxeldev.tgdrive.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * @author nvoxel
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun Form(
    formTitle: String,
    isLoading: Boolean,
    errorText: String?,
    retryCallback: () -> Unit,
    formContent: @Composable ColumnScope.() -> Unit,
) {
    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { Text(text = formTitle) },
            )
        },
    ) {  paddingValues ->
        errorText?.let {
            Error(
                message = it,
                shouldShowRetry = true,
                retryCallback = retryCallback,
            )
        } ?: run {
            if (isLoading) {
                Loader()
            } else {
                FormContent(
                    modifier = Modifier
                        .padding(paddingValues = paddingValues),
                    formContent = formContent,
                )
            }
        }
    }
}

@Composable
private fun FormContent(
    modifier: Modifier = Modifier,
    formContent: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier
            .padding(all = 24.dp),
    ) { formContent() }
}
