package com.voxeldev.tgdrive.content.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.voxeldev.tgdrive.Orientation
import com.voxeldev.tgdrive.components.CustomNavigationRail
import com.voxeldev.tgdrive.content.favorites.FavoritesContent
import com.voxeldev.tgdrive.content.files.FilesContent
import com.voxeldev.tgdrive.content.settings.SettingsContent
import com.voxeldev.tgdrive.files.FilesComponent
import com.voxeldev.tgdrive.getOrientation
import com.voxeldev.tgdrive.main.MainComponent
import com.voxeldev.tgdrive.theme.AdditionalIcons
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

private const val FAVORITES = "Favorites"
private const val FILES = "Files"
private const val SETTINGS = "Settings"

private val navigationTonalElevation = 3.0.dp

/**
 * @author nvoxel
 */
@Composable
internal fun MainContent(component: MainComponent) {
    when (getOrientation()) {
        Orientation.PORTRAIT -> VerticalLayout(component = component)
        else -> HorizontalLayout(component = component)
    }
}

@Composable
private fun VerticalLayout(component: MainComponent) {
    val childStack by component.childStack.subscribeAsState()
    val activeComponent = childStack.active.instance

    val fabClickCallback = remember { MutableSharedFlow<Unit>() }

    Scaffold(
        bottomBar = {
            BottomNavigation(component = component)
        },
        floatingActionButton = {
            (activeComponent as? MainComponent.Child.FilesChild)?.let { filesChild ->
                val filesChildStack by filesChild.component.childStack.subscribeAsState()
                if (filesChildStack.active.instance is FilesComponent.Child.ListChild) {
                    FloatingActionButton(fabClickCallback = fabClickCallback)
                }
            }
        }
    ) { paddingValues ->
        Children(
            component = component,
            modifier = Modifier.padding(
                bottom = paddingValues.calculateBottomPadding(),
            ),
            fabClickCallback = fabClickCallback,
        )
    }
}

@Composable
private fun HorizontalLayout(component: MainComponent) {
    val layoutDirection = LocalLayoutDirection.current
    val fabClickCallback = remember { MutableSharedFlow<Unit>() }

    Scaffold { paddingValues ->
        Row(
            modifier = Modifier
                .padding(
                    start = paddingValues.calculateStartPadding(layoutDirection = layoutDirection),
                    end = paddingValues.calculateEndPadding(layoutDirection = layoutDirection),
                    bottom = paddingValues.calculateBottomPadding(),
                )
        ) {
            SideNavigation(
                component = component,
                fab = {
                    FloatingActionButton(
                        modifier = Modifier
                            .size(size = 48.dp),
                        fabClickCallback = fabClickCallback,
                    )
                },
            )
            Children(
                component = component,
                fabClickCallback = fabClickCallback,
            )
        }
    }
}

@Composable
private fun Children(
    modifier: Modifier = Modifier,
    component: MainComponent,
    fabClickCallback: MutableSharedFlow<Unit>,
) {
    Children(
        stack = component.childStack,
        modifier = modifier,
        animation = stackAnimation(fade()),
    ) {
        when (val child = it.instance) {
            is MainComponent.Child.FavoritesChild -> FavoritesContent(component = child.component)
            is MainComponent.Child.FilesChild -> FilesContent(
                component = child.component,
                fabClickCallback = fabClickCallback,
            )

            is MainComponent.Child.SettingsChild -> SettingsContent(component = child.component)
        }
    }
}

@Composable
private fun BottomNavigation(
    modifier: Modifier = Modifier,
    component: MainComponent,
) {
    val childStack by component.childStack.subscribeAsState()
    val activeComponent = childStack.active.instance

    NavigationBar(
        modifier = modifier,
    ) {
        NavigationBarItem(
            selected = activeComponent is MainComponent.Child.FavoritesChild,
            onClick = component::onFavoritesTabClicked,
            icon = {
                Icon(
                    imageVector = AdditionalIcons.Star,
                    contentDescription = FAVORITES,
                )
            },
            label = {
                Text(text = FAVORITES)
            },
            alwaysShowLabel = false,
        )

        NavigationBarItem(
            selected = activeComponent is MainComponent.Child.FilesChild,
            onClick = component::onFilesTabClicked,
            icon = {
                Icon(
                    imageVector = AdditionalIcons.Folder,
                    contentDescription = FILES,
                )
            },
            label = { Text(text = FILES) },
            alwaysShowLabel = false,
        )

        NavigationBarItem(
            selected = activeComponent is MainComponent.Child.SettingsChild,
            onClick = component::onSettingsTabClicked,
            icon = {
                Icon(
                    imageVector = AdditionalIcons.Settings,
                    contentDescription = SETTINGS,
                )
            },
            label = { Text(text = SETTINGS) },
            alwaysShowLabel = false,
        )
    }
}

@Composable
private fun SideNavigation(
    modifier: Modifier = Modifier,
    component: MainComponent,
    fab: @Composable () -> Unit,
) {
    val childStack by component.childStack.subscribeAsState()
    val activeComponent = childStack.active.instance

    CustomNavigationRail(
        modifier = modifier
            .fillMaxHeight()
            .zIndex(zIndex = 1f),
        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(elevation = navigationTonalElevation),
        header = {
            (activeComponent as? MainComponent.Child.FilesChild)?.let { filesChild ->
                val filesChildStack by filesChild.component.childStack.subscribeAsState()
                if (filesChildStack.active.instance is FilesComponent.Child.ListChild) fab()
                else FloatingActionButtonPlaceholder()
            } ?: FloatingActionButtonPlaceholder()
        }
    ) {
        NavigationRailItem(
            selected = activeComponent is MainComponent.Child.FavoritesChild,
            onClick = component::onFavoritesTabClicked,
            icon = {
                Icon(
                    imageVector = AdditionalIcons.Star,
                    contentDescription = FAVORITES,
                )
            },
            label = { Text(text = FAVORITES) },
            alwaysShowLabel = false,
        )

        NavigationRailItem(
            selected = activeComponent is MainComponent.Child.FilesChild,
            onClick = component::onFilesTabClicked,
            icon = {
                Icon(
                    imageVector = AdditionalIcons.Folder,
                    contentDescription = FILES,
                )
            },
            label = { Text(text = FILES) },
            alwaysShowLabel = false,
        )

        NavigationRailItem(
            selected = activeComponent is MainComponent.Child.SettingsChild,
            onClick = component::onSettingsTabClicked,
            icon = {
                Icon(
                    imageVector = AdditionalIcons.Settings,
                    contentDescription = SETTINGS,
                )
            },
            label = { Text(text = SETTINGS) },
            alwaysShowLabel = false,
        )
    }
}

@Composable
private fun FloatingActionButton(
    modifier: Modifier = Modifier,
    fabClickCallback: MutableSharedFlow<Unit>,
) {
    val scope = rememberCoroutineScope()

    FloatingActionButton(
        modifier = modifier,
        onClick = { scope.launch { fabClickCallback.emit(Unit) } },
    ) { Icon(imageVector = AdditionalIcons.Add, contentDescription = null) }
}

@Composable
private fun FloatingActionButtonPlaceholder() {
    Box(modifier = Modifier.size(size = 48.dp))
}
