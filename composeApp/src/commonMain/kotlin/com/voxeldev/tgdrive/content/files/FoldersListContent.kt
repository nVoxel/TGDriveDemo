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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.voxeldev.tgdrive.components.ConfirmationDialog
import com.voxeldev.tgdrive.components.Error
import com.voxeldev.tgdrive.components.Loader
import com.voxeldev.tgdrive.components.RoundIcon
import com.voxeldev.tgdrive.components.TextFieldDialog
import com.voxeldev.tgdrive.files.api.folder.Folder
import com.voxeldev.tgdrive.files.list.FoldersListComponent
import com.voxeldev.tgdrive.main.api.chats.ChatInviteLink
import com.voxeldev.tgdrive.theme.AdditionalIcons
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

/**
 * @author nvoxel
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FoldersListContent(
    component: FoldersListComponent,
    fabClickCallback: MutableSharedFlow<Unit>,
) {
    val scope = rememberCoroutineScope()
    scope.launch {
        fabClickCallback.collect {
            component.onCreateFolderClicked()
        }
    }

    with(component) {
        val model by model.subscribeAsState()
        val state = rememberLazyListState()

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Files") },
                    actions = {
                        IconButton(onClick = ::onReloadClicked) {
                            Icon(imageVector = AdditionalIcons.RestartAlt, contentDescription = "Reload")
                        }
                    }
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
                    FoldersList(
                        modifier = Modifier
                            .padding(paddingValues = paddingValues),
                        state = state,
                        model = model,
                        onFolderClicked = ::onFolderClicked,
                        onOpenFolderMenu = ::onOpenFolderMenuClicked,
                        onDismissFolderMenu = ::onDismissFolderMenuClicked,
                        onRenameFolder = ::onRenameFolderClicked,
                        onShareFolder = ::onShareFolderClicked,
                        onDeleteFolder = ::onDeleteFolderClicked,
                    )

                    Dialogs(
                        createFolderDialogVisible = model.createFolderDialogVisible,
                        onConfirmCreateFolder = ::onConfirmCreateFolderClicked,
                        onDismissCreateFolder = ::onDismissCreateFolderClicked,
                        renameFolderDialogVisible = model.renameFolderDialogVisible,
                        onConfirmRenameFolder = ::onConfirmRenameFolderClicked,
                        onDismissRenameFolder = ::onDismissRenameFolderClicked,
                        deleteFolderDialogVisible = model.deleteFolderDialogVisible,
                        onConfirmDeleteFolder = ::onConfirmDeleteFolderClicked,
                        onDismissDeleteFolder = ::onDismissDeleteFolderClicked,
                        pendingFolderTitle = model.pendingFolderTitle,
                        onFolderTitleUpdate = ::onFolderTitleUpdate,
                    )
                }
            }
        }
    }
}

@Composable
private fun FoldersList(
    modifier: Modifier = Modifier,
    state: LazyListState,
    model: FoldersListComponent.Model,
    onFolderClicked: (Long, String) -> Unit,
    onOpenFolderMenu: (Long) -> Unit,
    onDismissFolderMenu: () -> Unit,
    onRenameFolder: () -> Unit,
    onShareFolder: (ChatInviteLink) -> Unit,
    onDeleteFolder: () -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        state = state,
    ) {
        items(
            items = model.folders,
            key = { folder -> folder.chatId }
        ) {
            FoldersListItem(
                folder = it,
                pendingFolderChatId = model.pendingFolderChatId,
                onItemClicked = onFolderClicked,
                folderMenuVisible = model.folderMenuVisible,
                onOpenFolderMenu = onOpenFolderMenu,
                onDismissFolderMenu = onDismissFolderMenu,
                onRenameFolder = onRenameFolder,
                onShareFolder = onShareFolder,
                onDeleteFolder = onDeleteFolder,
            )
        }
    }
}

@Composable
private fun FoldersListItem(
    folder: Folder,
    pendingFolderChatId: Long,
    onItemClicked: (Long, String) -> Unit,
    folderMenuVisible: Boolean,
    onOpenFolderMenu: (Long) -> Unit,
    onDismissFolderMenu: () -> Unit,
    onRenameFolder: () -> Unit,
    onShareFolder: (ChatInviteLink) -> Unit,
    onDeleteFolder: () -> Unit,
) {
    ElevatedCard(
        modifier = Modifier
            .padding(all = 12.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Row(
            modifier = Modifier
                .clickable(onClick = { onItemClicked(folder.chatId, folder.title) })
                .padding(all = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            RoundIcon(imageVector = AdditionalIcons.Folder, contentDescription = folder.title)

            Spacer(modifier = Modifier.width(width = 16.dp))

            Column(
                modifier = Modifier
                    .weight(weight = 1f),
            ) {
                Text(
                    text = folder.title,
                    style = MaterialTheme.typography.titleMedium,
                )

                Text(
                    text = "${folder.fileCount} files",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            Box {
                IconButton(
                    onClick = { onOpenFolderMenu(folder.chatId) },
                ) { Icon(imageVector = AdditionalIcons.MoreVert, contentDescription = "Open menu") }

                if (folder.chatId == pendingFolderChatId) {
                    FolderMenu(
                        folder = folder,
                        expanded = folderMenuVisible,
                        onDismiss = onDismissFolderMenu,
                        onRenameClicked = onRenameFolder,
                        onShareClicked = onShareFolder,
                        onDeleteClicked = onDeleteFolder,
                    )
                }
            }
        }
    }
}

@Composable
private fun FolderMenu(
    folder: Folder,
    expanded: Boolean,
    onDismiss: () -> Unit,
    onRenameClicked: () -> Unit,
    onShareClicked: (ChatInviteLink) -> Unit,
    onDeleteClicked: () -> Unit,
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss,
    ) {
        DropdownMenuItem(
            leadingIcon = { Icon(imageVector = AdditionalIcons.Edit, contentDescription = "Rename") },
            text = { Text(text = "Rename") },
            onClick = onRenameClicked,
        )

        folder.inviteLink?.let { chatInviteLink ->
            DropdownMenuItem(
                leadingIcon = { Icon(imageVector = AdditionalIcons.Share, contentDescription = "Share") },
                text = { Text(text = "Share") },
                onClick = { onShareClicked(chatInviteLink) },
            )
        }

        HorizontalDivider()

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
private fun Dialogs(
    createFolderDialogVisible: Boolean,
    onConfirmCreateFolder: () -> Unit,
    onDismissCreateFolder: () -> Unit,
    renameFolderDialogVisible: Boolean,
    onConfirmRenameFolder: () -> Unit,
    onDismissRenameFolder: () -> Unit,
    deleteFolderDialogVisible: Boolean,
    onConfirmDeleteFolder: () -> Unit,
    onDismissDeleteFolder: () -> Unit,
    pendingFolderTitle: String,
    onFolderTitleUpdate: (String) -> Unit,
) {
    TextFieldDialog(
        isVisible = createFolderDialogVisible,
        onDismiss = onDismissCreateFolder,
        text = pendingFolderTitle,
        textLabel = { Text(text = "Enter folder title") },
        onTextChange = onFolderTitleUpdate,
        confirmButtonContent = { Text(text = "Create Folder") },
        onConfirmClicked = onConfirmCreateFolder,
    )

    TextFieldDialog(
        isVisible = renameFolderDialogVisible,
        onDismiss = onDismissRenameFolder,
        text = pendingFolderTitle,
        textLabel = { Text(text = "Enter new folder title") },
        onTextChange = onFolderTitleUpdate,
        confirmButtonContent = { Text(text = "Rename Folder") },
        onConfirmClicked = onConfirmRenameFolder,
    )

    ConfirmationDialog(
        isVisible = deleteFolderDialogVisible,
        title = { Text(text = "Delete folder") },
        text = { Text(text = "Are you sure you want to delete this folder forever?") },
        onConfirm = onConfirmDeleteFolder,
        onDismiss = onDismissDeleteFolder,
    )
}
