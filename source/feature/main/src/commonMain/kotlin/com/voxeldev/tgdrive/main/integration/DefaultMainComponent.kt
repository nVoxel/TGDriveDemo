package com.voxeldev.tgdrive.main.integration

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.voxeldev.tgdrive.favorites.FavoritesComponent
import com.voxeldev.tgdrive.favorites.integration.DefaultFavoritesComponent
import com.voxeldev.tgdrive.files.DefaultFilesComponent
import com.voxeldev.tgdrive.files.FilesComponent
import com.voxeldev.tgdrive.files.api.platform.FileProvider
import com.voxeldev.tgdrive.files.api.platform.FileViewer
import com.voxeldev.tgdrive.files.api.platform.LinkHandler
import com.voxeldev.tgdrive.main.MainComponent
import com.voxeldev.tgdrive.main.MainComponent.Child
import com.voxeldev.tgdrive.settings.SettingsComponent
import com.voxeldev.tgdrive.settings.integration.DefaultSettingsComponent
import kotlinx.serialization.Serializable
import kotlinx.serialization.serializer
import org.koin.core.component.KoinComponent
import kotlin.coroutines.CoroutineContext

/**
 * @author nvoxel
 */
class DefaultMainComponent internal constructor(
    componentContext: ComponentContext,
    private val favoritesComponent: (ComponentContext) -> FavoritesComponent,
    private val filesComponent: (ComponentContext) -> FilesComponent,
    private val settingsComponent: (ComponentContext, (SettingsComponent.Output) -> Unit) -> SettingsComponent,
    private val output: (MainComponent.Output) -> Unit,
) : MainComponent, KoinComponent, ComponentContext by componentContext {

    constructor(
        componentContext: ComponentContext,
        mainContext: CoroutineContext,
        fileProvider: FileProvider?,
        fileViewer: FileViewer?,
        linkHandler: LinkHandler?,
        storeFactory: StoreFactory,
        output: (MainComponent.Output) -> Unit,
    ) : this(
        componentContext = componentContext,
        favoritesComponent = { childContext ->
            DefaultFavoritesComponent(
                componentContext = childContext,
                storeFactory = storeFactory,
                fileViewer = fileViewer,
            )
        },
        filesComponent = { childContext ->
            DefaultFilesComponent(
                componentContext = childContext,
                storeFactory = storeFactory,
                mainContext = mainContext,
                fileViewer = fileViewer,
                fileProvider = fileProvider,
                linkHandler = linkHandler,
            )
        },
        settingsComponent = { childContext, settingsOutput ->
            DefaultSettingsComponent(
                componentContext = childContext,
                storeFactory = storeFactory,
                output = settingsOutput,
            )
        },
        output = output,
    )

    private val navigation = StackNavigation<Config>()

    override val childStack: Value<ChildStack<*, Child>> =
        childStack(
            source = navigation,
            serializer = serializer(),
            initialConfiguration = Config.Files,
            handleBackButton = true,
            childFactory = ::createChild,
        )

    override fun onFavoritesTabClicked() {
        navigation.bringToFront(Config.Favorites)
    }

    override fun onFilesTabClicked() {
        navigation.bringToFront(Config.Files)
    }

    override fun onSettingsTabClicked() {
        navigation.bringToFront(Config.Settings)
    }

    private fun createChild(config: Config, componentContext: ComponentContext): Child =
        when (config) {
            is Config.Favorites -> Child.FavoritesChild(
                component = favoritesComponent(componentContext),
            )

            is Config.Files -> Child.FilesChild(
                component = filesComponent(componentContext),
            )

            is Config.Settings -> Child.SettingsChild(
                component = settingsComponent(componentContext, ::onSettingsOutput),
            )
        }

    private fun onSettingsOutput(output: SettingsComponent.Output) = when (output) {
        is SettingsComponent.Output.LoggedOut -> output(MainComponent.Output.LoggedOut)
    }

    @Serializable
    private sealed class Config {
        @Serializable
        data object Favorites : Config()

        @Serializable
        data object Files : Config()

        @Serializable
        data object Settings : Config()
    }
}
