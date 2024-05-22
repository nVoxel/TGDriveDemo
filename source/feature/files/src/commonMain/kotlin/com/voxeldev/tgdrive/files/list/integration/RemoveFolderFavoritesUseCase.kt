package com.voxeldev.tgdrive.files.list.integration

import com.voxeldev.tgdrive.favorites.api.FavoritesRepository
import com.voxeldev.tgdrive.utils.integration.BaseUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * @author nvoxel
 */
class RemoveFolderFavoritesUseCase : BaseUseCase<Long, Unit>(), KoinComponent {

    private val favoritesRepository: FavoritesRepository by inject()

    override suspend fun run(params: Long) = favoritesRepository.deleteByFolder(chatId = params)
}
