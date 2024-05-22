package com.voxeldev.tgdrive.files.api.platform

import com.voxeldev.tgdrive.main.api.files.File

/**
 * @author nvoxel
 */
fun interface FileViewer {

    fun openFile(file: File)
}
