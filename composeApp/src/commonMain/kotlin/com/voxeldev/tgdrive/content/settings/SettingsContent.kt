package com.voxeldev.tgdrive.content.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.voxeldev.tgdrive.components.ConfirmationDialog
import com.voxeldev.tgdrive.components.Error
import com.voxeldev.tgdrive.components.Loader
import com.voxeldev.tgdrive.settings.SettingsComponent

/**
 * @author nvoxel
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SettingsContent(component: SettingsComponent) {
    with(component) {
        val model by model.subscribeAsState()
        val state = rememberLazyListState()

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Settings") }
                )
            }
        ) { paddingValues ->
            if (model.isLoading) {
                Loader()
            } else {
                model.errorText?.let {
                    Error(
                        message = it,
                        shouldShowRetry = true,
                        retryCallback = ::onReloadClicked,
                    )
                } ?: run {
                    SettingsList(
                        modifier = Modifier
                            .padding(paddingValues = paddingValues),
                        state = state,
                        onLogOut = ::onLogOutClicked,
                    )

                    Dialogs(
                        logOutDialogVisible = model.logOutDialogVisible,
                        onConfirmLogOut = ::onConfirmLogOutClicked,
                        onDismissLogOut = ::onDismissLogOutClicked,
                    )
                }
            }
        }
    }
}

@Composable
private fun SettingsList(
    modifier: Modifier = Modifier,
    state: LazyListState,
    onLogOut: () -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        state = state,
    ) {
        item {
            SettingsListItem(
                icon = { Icon(imageVector = Icons.Default.Sync, contentDescription = "Set up sync") },
                title = {
                    Text(
                        text = "Set up sync",
                        style = MaterialTheme.typography.titleMedium,
                    )
                },
                description = {
                    Text(
                        text = "Set up scheduled synchronization",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                },
                onClick = { },
            )
        }

        item {
            HorizontalDivider()

            SettingsListItem(
                icon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.Logout,
                        contentDescription = "Log out",
                        tint = MaterialTheme.colorScheme.error,
                    )
                },
                title = {
                    Text(
                        text = "Log out",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.error,
                    )
                },
                description = {
                    Text(
                        text = "Log out from active account",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error,
                    )
                },
                onClick = onLogOut,
            )
        }
    }
}

@Composable
private fun SettingsListItem(
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit,
    title: @Composable () -> Unit,
    description: @Composable () -> Unit,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(all = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        icon()

        Spacer(modifier = Modifier.width(width = 15.dp))

        Column {
            title()
            description()
        }
    }
}

@Composable
private fun Dialogs(
    logOutDialogVisible: Boolean,
    onConfirmLogOut: () -> Unit,
    onDismissLogOut: () -> Unit,
) {
    ConfirmationDialog(
        isVisible = logOutDialogVisible,
        title = { Text(text = "Log out") },
        text = { Text(text = "Are you sure you want to log out? This will delete all client data.") },
        onConfirm = onConfirmLogOut,
        onDismiss = onDismissLogOut,
    )
}
