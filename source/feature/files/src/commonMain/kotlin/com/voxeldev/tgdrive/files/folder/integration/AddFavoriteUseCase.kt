package com.voxeldev.tgdrive.files.folder.integration

import com.voxeldev.tgdrive.favorites.api.FavoriteMessage
import com.voxeldev.tgdrive.favorites.api.FavoritesRepository
import com.voxeldev.tgdrive.utils.integration.BaseUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * @author nvoxel
 */
class AddFavoriteUseCase : BaseUseCase<FavoriteMessage, Long>(), KoinComponent {

    private val favoritesRepository: FavoritesRepository by inject()

    override suspend fun run(params: FavoriteMessage) = favoritesRepository.insert(favoriteMessage = params)
}
