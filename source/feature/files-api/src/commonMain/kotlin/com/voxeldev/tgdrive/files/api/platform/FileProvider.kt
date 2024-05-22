package com.voxeldev.tgdrive.files.api.platform

import kotlinx.coroutines.flow.Flow

/**
 * @author nvoxel
 */
interface FileProvider {

    val providedFilesFlow: Flow<String>

    fun onProvide()
}
