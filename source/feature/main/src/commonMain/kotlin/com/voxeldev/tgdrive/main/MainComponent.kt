package com.voxeldev.tgdrive.main

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.voxeldev.tgdrive.favorites.FavoritesComponent
import com.voxeldev.tgdrive.files.FilesComponent
import com.voxeldev.tgdrive.settings.SettingsComponent

/**
 * @author nvoxel
 */
interface MainComponent {

    val childStack: Value<ChildStack<*, Child>>

    fun onFavoritesTabClicked()
    fun onFilesTabClicked()
    fun onSettingsTabClicked()

    sealed interface Child {
        class FavoritesChild(val component: FavoritesComponent) : Child
        class FilesChild(val component: FilesComponent) : Child
        class SettingsChild(val component: SettingsComponent) : Child
    }

    sealed interface Output {
        data object LoggedOut : Output
    }
}
