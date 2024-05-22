package com.voxeldev.tgdrive.favorites

import com.arkivanov.decompose.value.Value
import com.voxeldev.tgdrive.favorites.api.FavoriteMessage
import com.voxeldev.tgdrive.files.api.file.TGDriveFile
import com.voxeldev.tgdrive.main.api.files.File

/**
 * @author nvoxel
 */
interface FavoritesComponent {

    val model: Value<Model>

    fun onFileClicked(file: File)

    fun onReloadClicked()

    fun onRemoveFavoriteClicked(favoriteMessage: FavoriteMessage)

    data class Model(
        val favoriteFiles: List<TGDriveFile>,
        val favoriteFilesHash: Int,
        val errorText: String?,
        val isLoading: Boolean,
    )
}
