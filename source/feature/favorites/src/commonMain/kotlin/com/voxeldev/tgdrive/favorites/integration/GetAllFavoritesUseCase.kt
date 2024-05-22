package com.voxeldev.tgdrive.favorites.integration

import com.voxeldev.tgdrive.favorites.api.FavoriteMessage
import com.voxeldev.tgdrive.favorites.api.FavoritesRepository
import com.voxeldev.tgdrive.utils.integration.BaseUseCase
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * @author nvoxel
 */
class GetAllFavoritesUseCase : BaseUseCase<BaseUseCase.NoParams, Flow<List<FavoriteMessage>>>(), KoinComponent {

    private val favoritesRepository: FavoritesRepository by inject()

    override suspend fun run(params: NoParams): Flow<List<FavoriteMessage>> = favoritesRepository.getAll()
}
