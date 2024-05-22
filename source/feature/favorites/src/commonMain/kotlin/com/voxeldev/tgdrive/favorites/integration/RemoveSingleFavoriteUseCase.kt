package com.voxeldev.tgdrive.favorites.integration

import com.voxeldev.tgdrive.favorites.api.FavoritesRepository
import com.voxeldev.tgdrive.utils.integration.BaseUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * @author nvoxel
 */
class RemoveSingleFavoriteUseCase : BaseUseCase<Int, Unit>(), KoinComponent {

    private val favoritesRepository: FavoritesRepository by inject()

    override suspend fun run(params: Int)= favoritesRepository.deleteSingle(id = params)
}
