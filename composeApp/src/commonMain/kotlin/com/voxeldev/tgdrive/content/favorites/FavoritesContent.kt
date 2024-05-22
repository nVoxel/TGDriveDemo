package com.voxeldev.tgdrive.content.favorites

import androidx.compose.foundation.clickable
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
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.voxeldev.tgdrive.components.Error
import com.voxeldev.tgdrive.components.Loader
import com.voxeldev.tgdrive.components.RoundIcon
import com.voxeldev.tgdrive.favorites.FavoritesComponent
import com.voxeldev.tgdrive.favorites.api.FavoriteMessage
import com.voxeldev.tgdrive.files.api.file.TGDriveFile
import com.voxeldev.tgdrive.main.api.files.File
import com.voxeldev.tgdrive.theme.AdditionalIcons

/**
 * @author nvoxel
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FavoritesContent(component: FavoritesComponent) {
    with(component) {
        val model by model.subscribeAsState()
        val state = rememberLazyListState()

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Favorites") },
                    actions = {
                        IconButton(onClick = ::onReloadClicked) {
                            Icon(imageVector = AdditionalIcons.RestartAlt, contentDescription = "Reload")
                        }
                    }
                )
            },
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
                    FavoritesList(
                        modifier = Modifier
                            .padding(paddingValues = paddingValues),
                        state = state,
                        model = model,
                        onClicked = ::onFileClicked,
                        onRemoveFavorite = ::onRemoveFavoriteClicked,
                    )
                }
            }
        }
    }
}

@Composable
private fun FavoritesList(
    modifier: Modifier = Modifier,
    state: LazyListState,
    model: FavoritesComponent.Model,
    onClicked: (File) -> Unit,
    onRemoveFavorite: (FavoriteMessage) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        state = state,
    ) {
        items(
            items = model.favoriteFiles,
            key = { favorite -> favorite.messageFile.file.id }
        ) {
            FavoritesListItem(
                favorite = it,
                onClicked = onClicked,
                onRemoveFavorite = onRemoveFavorite,
            )
        }
    }
}

@Composable
private fun FavoritesListItem(
    favorite: TGDriveFile,
    onClicked: (File) -> Unit,
    onRemoveFavorite: (FavoriteMessage) -> Unit,
) {
    ElevatedCard(
        modifier = Modifier
            .padding(all = 12.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Row(
            modifier = Modifier
                .clickable { onClicked(favorite.messageFile.file) }
                .padding(all = 24.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            RoundIcon(imageVector = AdditionalIcons.Description, contentDescription = "Favorite File")

            Spacer(modifier = Modifier.width(width = 16.dp))

            Column(
                modifier = Modifier
                    .weight(weight = 1f),
            ) {
                Text(
                    text = favorite.messageFile.fileName,
                    style = MaterialTheme.typography.titleMedium,
                )

                Text(
                    text = "Size: ${favorite.messageFile.file.expectedSize} bytes",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            favorite.favoriteMessage?.let { favoriteMessage ->
                IconButton(
                    onClick = { onRemoveFavorite(favoriteMessage) },
                ) {
                    Icon(
                        imageVector = AdditionalIcons.StarFilled,
                        contentDescription = "Add to favorites",
                    )
                }
            }
        }
    }
}
