package com.voxeldev.tgdrive.favorites.integration

import com.voxeldev.tgdrive.favorites.FavoritesComponent
import com.voxeldev.tgdrive.favorites.store.FavoritesStore.State

/**
 * @author nvoxel
 */
class StateMapper {

    fun toModel(state: State): FavoritesComponent.Model =
        FavoritesComponent.Model(
            favoriteFiles = state.favoriteFiles,
            favoriteFilesHash = state.favoriteFiles.hashCode(),
            errorText = state.errorText,
            isLoading = state.isLoading,
        )
}
