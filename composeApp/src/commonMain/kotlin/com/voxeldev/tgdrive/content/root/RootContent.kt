package com.voxeldev.tgdrive.content.root

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.voxeldev.tgdrive.content.auth.AuthContent
import com.voxeldev.tgdrive.content.main.MainContent
import com.voxeldev.tgdrive.root.RootComponent
import com.voxeldev.tgdrive.theme.TGDriveTheme

/**
 * @author nvoxel
 */
@Composable
fun RootContent(component: RootComponent) {
    TGDriveTheme {
        Scaffold { paddingValues ->
            Children(
                component = component,
                modifier = Modifier
                    .padding(paddingValues = paddingValues)
            )
        }
    }
}

@Composable
private fun Children(component: RootComponent, modifier: Modifier = Modifier) {
    Children(
        stack = component.childStack,
        modifier = modifier,
        animation = stackAnimation(fade() + scale()),
    ) {
        when (val child = it.instance) {
            is RootComponent.Child.AuthChild -> AuthContent(component = child.component)
            is RootComponent.Child.MainChild -> MainContent(component = child.component)
        }
    }
}
