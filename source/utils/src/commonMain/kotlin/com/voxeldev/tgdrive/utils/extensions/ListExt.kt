package com.voxeldev.tgdrive.utils.extensions

/**
 * @author nvoxel
 */
fun <T> List<T>.indexOrNull(predicate: (T) -> Boolean): Int? {
    var index = 0
    for (item in this) {
        if (predicate(item))
            return index
        index++
    }
    return null
}
