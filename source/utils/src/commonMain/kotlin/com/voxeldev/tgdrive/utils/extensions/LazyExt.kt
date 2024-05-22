package com.voxeldev.tgdrive.utils.extensions

/**
 * @author nvoxel
 */
fun <T> lazyUnsafe(initializer: () -> T) = lazy(mode = LazyThreadSafetyMode.NONE, initializer = initializer)
