package com.voxeldev.tgdrive.content.files

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.voxeldev.tgdrive.files.FilesComponent
import kotlinx.coroutines.flow.MutableSharedFlow

/**
 * @author nvoxel
 */
@Composable
internal fun FilesContent(
    component: FilesComponent,
    fabClickCallback: MutableSharedFlow<Unit>,
) {
    Children(
        modifier = Modifier
            .fillMaxSize(),
        component = component,
        fabClickCallback = fabClickCallback,
    )
}

@Composable
private fun Children(
    modifier: Modifier = Modifier,
    component: FilesComponent,
    fabClickCallback: MutableSharedFlow<Unit>,
) {
    Children(
        modifier = modifier,
        stack = component.childStack,
        animation = stackAnimation(fade())
    ) {
        when (val child = it.instance) {
            is FilesComponent.Child.ListChild -> FoldersListContent(
                component = child.component,
                fabClickCallback = fabClickCallback,
            )
            is FilesComponent.Child.FolderChild -> FolderContent(component = child.component)
        }
    }
}
