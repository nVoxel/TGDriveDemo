package com.voxeldev.tgdrive.content.files

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
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
import com.voxeldev.tgdrive.components.ModalBottomSheet
import com.voxeldev.tgdrive.components.RoundIcon
import com.voxeldev.tgdrive.components.conditional
import com.voxeldev.tgdrive.files.api.file.LocalFile
import com.voxeldev.tgdrive.files.api.file.TGDriveFile
import com.voxeldev.tgdrive.files.folder.FolderComponent
import com.voxeldev.tgdrive.theme.AdditionalIcons

/**
 * @author nvoxel
 */
@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun FolderContent(component: FolderComponent) {
    with(component) {
        val model by model.subscribeAsState()
        val state = rememberLazyListState()

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = model.folderName) },
                    navigationIcon = {
                        IconButton(onClick = ::onCloseClicked) {
                            Icon(imageVector = AdditionalIcons.ArrowBack, "Back")
                        }
                    },
                    actions = {
                        IconButton(onClick = ::onUploadsBottomSheetToggle) {
                            Icon(imageVector = AdditionalIcons.Upload, contentDescription = "Upload Files")
                        }

                        IconButton(onClick = ::onReloadClicked) {
                            Icon(imageVector = AdditionalIcons.RestartAlt, contentDescription = "Reload")
                        }
                    },
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
                    ChatFilesList(
                        modifier = Modifier
                            .padding(paddingValues = paddingValues),
                        state = state,
                        model = model,
                        onFileClicked = ::onFileClicked,
                        fileMenuVisible = model.fileMenuVisible,
                        onOpenFileMenu = ::onOpenFileMenuClicked,
                        onDismissFileMenu = ::onDismissFileMenuClicked,
                        onDeleteFile = ::onDeleteFileClicked,
                        pendingFileMessageId = model.pendingFileMessageId,
                        onFileFavoriteClicked = ::onFileFavoriteClicked,
                    )

                    DeleteFileDialog(
                        deleteFileDialogVisible = model.deleteFileDialogVisible,
                        onConfirmDeleteFile = ::onConfirmDeleteFileClicked,
                        onDismissDeleteFile = ::onDismissDeleteFileClicked,
                    )

                    UploadsBottomSheet(
                        modifier = Modifier
                            .padding(paddingValues = paddingValues),
                        isVisible = model.uploadsBottomSheetActive,
                        onDismissRequest = ::onUploadsBottomSheetToggle,
                        uploadingFiles = model.uploadingFiles,
                        onSelectButtonClicked = ::onUploadFileSelect,
                    )
                }
            }
        }
    }
}

@Composable
private fun ChatFilesList(
    modifier: Modifier = Modifier,
    state: LazyListState,
    model: FolderComponent.Model,
    onFileClicked: (Int) -> Unit,
    fileMenuVisible: Boolean,
    onOpenFileMenu: (Long) -> Unit,
    onDismissFileMenu: () -> Unit,
    onDeleteFile: () -> Unit,
    pendingFileMessageId: Long,
    onFileFavoriteClicked: (Long) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        state = state,
    ) {
        items(
            items = model.files,
            key = { file -> file.messageFile.file.id },
        ) {
            ChatFileItem(
                file = it,
                downloadingFiles = model.downloadingFiles,
                onItemClicked = onFileClicked,
                fileMenuVisible = fileMenuVisible,
                onOpenFileMenu = onOpenFileMenu,
                onDismissFileMenu = onDismissFileMenu,
                onDeleteFile = onDeleteFile,
                pendingFileMessageId = pendingFileMessageId,
                onFileFavoriteClicked = onFileFavoriteClicked,
            )
        }
    }
}

@Composable
private fun ChatFileItem(
    file: TGDriveFile,
    downloadingFiles: Map<Int, Float>,
    onItemClicked: (Int) -> Unit,
    fileMenuVisible: Boolean,
    onOpenFileMenu: (Long) -> Unit,
    onDismissFileMenu: () -> Unit,
    onDeleteFile: () -> Unit,
    pendingFileMessageId: Long,
    onFileFavoriteClicked: (Long) -> Unit,
) {
    ElevatedCard(
        modifier = Modifier
            .padding(all = 12.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Row(
            modifier = Modifier
                .conditional(
                    condition = !downloadingFiles.containsKey(file.messageFile.file.id),
                    conditionMetModifier = Modifier.clickable { onItemClicked(file.messageFile.file.id) }
                )
                .padding(all = 24.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            RoundIcon(imageVector = AdditionalIcons.Description, contentDescription = file.messageFile.fileName)

            Spacer(modifier = Modifier.width(width = 16.dp))

            Column(
                modifier = Modifier
                    .weight(weight = 1f),
            ) {
                Text(
                    text = file.messageFile.fileName,
                    style = MaterialTheme.typography.titleMedium,
                )

                Text(
                    text = "Size: ${file.messageFile.file.expectedSize} bytes",
                    style = MaterialTheme.typography.bodyMedium,
                )

                Text(
                    text = "Downloaded: ${if (file.messageFile.file.isDownloaded()) "Yes" else "No"}",
                    style = MaterialTheme.typography.bodyMedium,
                )

                file.messageFile.caption?.let { caption ->
                    if (caption.isNotBlank())
                        Text(
                            text = caption,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                }

                if (downloadingFiles.containsKey(file.messageFile.file.id)) {
                    LinearProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        progress = { downloadingFiles[file.messageFile.file.id]!! }
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (file.messageFile.file.isDownloaded()) {
                    IconButton(
                        onClick = { onFileFavoriteClicked(file.messageFile.messageId) },
                    ) {
                        Icon(
                            imageVector = if (file.favoriteMessage == null) AdditionalIcons.Star else AdditionalIcons.StarFilled,
                            contentDescription = "Add to favorites",
                        )
                    }
                }

                Box {
                    IconButton(
                        onClick = { onOpenFileMenu(file.messageFile.messageId) },
                    ) { Icon(imageVector = AdditionalIcons.MoreVert, contentDescription = "Open menu") }

                    if (file.messageFile.messageId == pendingFileMessageId) {
                        FileMenu(
                            expanded = fileMenuVisible,
                            onDismiss = onDismissFileMenu,
                            onDeleteClicked = onDeleteFile,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FileMenu(
    expanded: Boolean,
    onDismiss: () -> Unit,
    onDeleteClicked: () -> Unit,
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss,
    ) {
        DropdownMenuItem(
            leadingIcon = {
                Icon(imageVector = AdditionalIcons.DeleteForever, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error)
            },
            text = { Text(text = "Delete", color = MaterialTheme.colorScheme.error) },
            onClick = onDeleteClicked,
        )
    }
}

@Composable
private fun UploadsBottomSheet(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    onDismissRequest: () -> Unit,
    uploadingFiles: List<LocalFile>,
    onSelectButtonClicked: () -> Unit,
) {
    ModalBottomSheet(
        modifier = modifier,
        isVisible = isVisible,
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(
                onClick = onSelectButtonClicked,
            ) {
                Icon(imageVector = AdditionalIcons.SelectUploadFile, contentDescription = "Select a file")
                Text(text = "Select a file")
            }

            UploadingFilesList(
                uploadingFiles = uploadingFiles,
            )
        }
    }
}

@Composable
private fun UploadingFilesList(
    uploadingFiles: List<LocalFile>,
) {
    val state = rememberLazyListState()

    LazyColumn(
        state = state
    ) {
        items(
            items = uploadingFiles,
            key = { localFile -> localFile.id ?: 0 }
        ) { localFile ->
            UploadingFileItem(localFile = localFile)
        }
    }
}

@Composable
private fun UploadingFileItem(
    localFile: LocalFile,
) {
    ElevatedCard(
        modifier = Modifier
            .padding(all = 12.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(all = 24.dp)
                .fillMaxWidth(),
        ) {
            Text(text = localFile.name)

            localFile.uploadProgress?.let {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    progress = { it }
                )
            }
        }
    }
}

@Composable
private fun DeleteFileDialog(
    deleteFileDialogVisible: Boolean,
    onConfirmDeleteFile: () -> Unit,
    onDismissDeleteFile: () -> Unit,
) {
    ConfirmationDialog(
        isVisible = deleteFileDialogVisible,
        title = { Text(text = "Delete file") },
        text = { Text(text = "Are you sure you want to delete this file from the folder?") },
        onConfirm = onConfirmDeleteFile,
        onDismiss = onDismissDeleteFile,
    )
}
