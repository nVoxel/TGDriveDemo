package com.voxeldev.tgdrive.root.integration

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.voxeldev.tgdrive.auth.AuthComponent
import com.voxeldev.tgdrive.auth.DefaultAuthComponent
import com.voxeldev.tgdrive.files.api.platform.FileProvider
import com.voxeldev.tgdrive.files.api.platform.FileViewer
import com.voxeldev.tgdrive.files.api.platform.LinkHandler
import com.voxeldev.tgdrive.main.MainComponent
import com.voxeldev.tgdrive.main.integration.DefaultMainComponent
import com.voxeldev.tgdrive.root.RootComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.Serializable
import kotlinx.serialization.serializer
import org.koin.core.component.KoinComponent
import kotlin.coroutines.CoroutineContext

/**
 * @author nvoxel
 */
class DefaultRootComponent internal constructor(
    componentContext: ComponentContext,
    private val authComponent: (ComponentContext, (AuthComponent.Output) -> Unit) -> AuthComponent,
    private val mainComponent: (ComponentContext, (MainComponent.Output) -> Unit) -> MainComponent,
) : RootComponent, KoinComponent, ComponentContext by componentContext {

    constructor(
        componentContext: ComponentContext,
        fileProvider: FileProvider?,
        fileViewer: FileViewer?,
        linkHandler: LinkHandler?,
        storeFactory: StoreFactory,
        mainContext: CoroutineContext,
    ) : this(
        componentContext = componentContext,
        authComponent = { childContext, authOutput ->
            DefaultAuthComponent(
                componentContext = childContext,
                storeFactory = storeFactory,
                mainContext = mainContext,
                output = authOutput,
            )
        },
        mainComponent = { childContext, mainOutput ->
            DefaultMainComponent(
                componentContext = childContext,
                mainContext = mainContext,
                fileProvider = fileProvider,
                fileViewer = fileViewer,
                linkHandler = linkHandler,
                storeFactory = storeFactory,
                output = mainOutput,
            )
        }
    )

    // iOS constructor
    @Suppress("unused")
    constructor(
        componentContext: ComponentContext,
        storeFactory: StoreFactory,
    ) : this(componentContext, null, null, null, storeFactory, Dispatchers.Main)

    private val navigation = StackNavigation<Config>()

    override val childStack: Value<ChildStack<*, RootComponent.Child>> =
        childStack(
            source = navigation,
            serializer = serializer(),
            initialConfiguration = Config.Auth,
            handleBackButton = true,
            childFactory = ::createChild,
        )

    private fun createChild(config: Config, componentContext: ComponentContext): RootComponent.Child =
        when (config) {
            is Config.Main -> RootComponent.Child.MainChild(
                component = mainComponent(componentContext, ::onMainOutput)
            )

            is Config.Auth -> RootComponent.Child.AuthChild(
                component = authComponent(componentContext, ::onAuthOutput)
            )
        }

    private fun onMainOutput(output: MainComponent.Output) =
        when (output) {
            is MainComponent.Output.LoggedOut -> navigation.replaceAll(Config.Auth)
        }

    private fun onAuthOutput(output: AuthComponent.Output) =
        when (output) {
            is AuthComponent.Output.Success -> navigation.replaceAll(Config.Main)
        }

    @Serializable
    private sealed class Config {
        @Serializable
        data object Auth : Config()

        @Serializable
        data object Main : Config()
    }
}
