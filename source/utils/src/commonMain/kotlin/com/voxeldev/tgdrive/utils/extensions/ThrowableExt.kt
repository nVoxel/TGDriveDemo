package com.voxeldev.tgdrive.utils.extensions

/**
 * @author nvoxel
 */
fun Throwable.getMessage() = message ?: toString()
