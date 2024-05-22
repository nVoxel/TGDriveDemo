package com.voxeldev.tgdrive.files

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.voxeldev.tgdrive.files.folder.FolderComponent
import com.voxeldev.tgdrive.files.list.FoldersListComponent

/**
 * @author nvoxel
 */
interface FilesComponent {

    val childStack: Value<ChildStack<*, Child>>

    sealed interface Child {
        class ListChild(val component: FoldersListComponent) : Child
        class FolderChild(val component: FolderComponent) : Child
    }
}
