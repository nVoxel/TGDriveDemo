package com.voxeldev.tgdrive.settings.integration

import com.voxeldev.tgdrive.favorites.api.FavoritesRepository
import com.voxeldev.tgdrive.utils.integration.BaseUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * @author nvoxel
 */
class ClearFavoritesUseCase : BaseUseCase<BaseUseCase.NoParams, Unit>(), KoinComponent {

    private val favoritesRepository: FavoritesRepository by inject()

    override suspend fun run(params: NoParams) = favoritesRepository.clearTable()
}
