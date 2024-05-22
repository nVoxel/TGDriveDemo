package com.voxeldev.tgdrive.favorites.integration

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.voxeldev.tgdrive.favorites.FavoritesComponent
import com.voxeldev.tgdrive.favorites.api.FavoriteMessage
import com.voxeldev.tgdrive.favorites.store.FavoritesStore
import com.voxeldev.tgdrive.favorites.store.FavoritesStoreProvider
import com.voxeldev.tgdrive.files.api.platform.FileViewer
import com.voxeldev.tgdrive.main.api.files.File
import com.voxeldev.tgdrive.utils.extensions.asValue
import org.koin.core.component.KoinComponent

/**
 * @author nvoxel
 */
class DefaultFavoritesComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val fileViewer: FileViewer?,
) : FavoritesComponent, KoinComponent, ComponentContext by componentContext {

    private val stateMapper = StateMapper()

    private val store = instanceKeeper.getStore {
        FavoritesStoreProvider(
            storeFactory = storeFactory,
        ).provide()
    }

    override val model: Value<FavoritesComponent.Model> = store.asValue().map { state -> stateMapper.toModel(state = state) }

    override fun onFileClicked(file: File) {
        fileViewer?.openFile(file = file)
    }

    override fun onReloadClicked() = store.accept(intent = FavoritesStore.Intent.Reload)

    override fun onRemoveFavoriteClicked(favoriteMessage: FavoriteMessage) =
        store.accept(intent = FavoritesStore.Intent.FileFavoriteClicked(id = favoriteMessage.id))
}
