package com.voxeldev.tgdrive.files.api.file

import com.voxeldev.tgdrive.favorites.api.FavoriteMessage

/**
 * @author nvoxel
 */
// TODO: move such classes to implementation modules to remove api-api dependencies
data class TGDriveFile(
    val messageFile: MessageFile,
    val favoriteMessage: FavoriteMessage?,
)
