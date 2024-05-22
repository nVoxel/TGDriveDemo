package com.voxeldev.tgdrive.favorites.store

import com.arkivanov.mvikotlin.core.store.Store
import com.voxeldev.tgdrive.favorites.api.FavoriteMessage
import com.voxeldev.tgdrive.favorites.store.FavoritesStore.Intent
import com.voxeldev.tgdrive.favorites.store.FavoritesStore.State
import com.voxeldev.tgdrive.files.api.file.TGDriveFile


/**
 * @author nvoxel
 */
interface FavoritesStore : Store<Intent, State, Nothing> {

    sealed interface Intent {
        data class FileFavoriteClicked(val id: Int) : Intent

        data object Reload : Intent
    }

    data class State(
        val favorites: List<FavoriteMessage> = mutableListOf(),
        val favoriteFiles: MutableList<TGDriveFile> = mutableListOf(),
        val errorText: String? = null,
        val isLoading: Boolean = false,
    )
}
