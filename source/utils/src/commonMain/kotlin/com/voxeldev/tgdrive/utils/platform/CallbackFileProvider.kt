package com.voxeldev.tgdrive.utils.platform

import com.voxeldev.tgdrive.files.api.platform.FileProvider
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

/**
 * @author nvoxel
 */
class CallbackFileProvider(
    private val onProvideRequest: () -> Unit,
) : FileProvider {

    lateinit var onProvideResponse: (String) -> Unit

    override val providedFilesFlow: Flow<String> = callbackFlow {
        onProvideResponse = {
            trySend(it)
        }

        awaitClose { }
    }

    override fun onProvide() = onProvideRequest()
}
