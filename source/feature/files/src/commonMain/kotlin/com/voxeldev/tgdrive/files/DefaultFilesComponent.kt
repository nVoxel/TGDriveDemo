package com.voxeldev.tgdrive.files

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.voxeldev.tgdrive.files.api.platform.FileProvider
import com.voxeldev.tgdrive.files.api.platform.FileViewer
import com.voxeldev.tgdrive.files.api.platform.LinkHandler
import com.voxeldev.tgdrive.files.folder.FolderComponent
import com.voxeldev.tgdrive.files.folder.integration.DefaultFolderComponent
import com.voxeldev.tgdrive.files.list.FoldersListComponent
import com.voxeldev.tgdrive.files.list.integration.DefaultFoldersListComponent
import kotlinx.serialization.Serializable
import kotlinx.serialization.serializer
import kotlin.coroutines.CoroutineContext

/**
 * @author nvoxel
 */
class DefaultFilesComponent internal constructor(
    componentContext: ComponentContext,
    private val foldersListComponent: (ComponentContext, (FoldersListComponent.Output) -> Unit) -> FoldersListComponent,
    private val folderComponent: (ComponentContext, Long, String, () -> Unit) -> FolderComponent,
) : FilesComponent, ComponentContext by componentContext {

    constructor(
        componentContext: ComponentContext,
        storeFactory: StoreFactory,
        mainContext: CoroutineContext,
        fileViewer: FileViewer?,
        fileProvider: FileProvider?,
        linkHandler: LinkHandler?,
    ) : this(
        componentContext = componentContext,
        foldersListComponent = { childContext, output ->
            DefaultFoldersListComponent(
                componentContext = childContext,
                storeFactory = storeFactory,
                linkHandler = linkHandler,
                output = output,
            )
        },
        folderComponent = { childContext, folderId, folderName, onFolderCloseClicked ->
            DefaultFolderComponent(
                folderId = folderId,
                folderName = folderName,
                componentContext = childContext,
                mainContext = mainContext,
                storeFactory = storeFactory,
                fileViewer = fileViewer,
                fileProvider = fileProvider,
                onCloseCallback = onFolderCloseClicked,
            )
        }
    )

    private val navigation = StackNavigation<Config>()

    override val childStack: Value<ChildStack<*, FilesComponent.Child>> =
        childStack(
            source = navigation,
            serializer = serializer(),
            initialConfiguration = Config.FoldersList,
            handleBackButton = true,
            childFactory = ::createChild,
        )

    private fun createChild(config: Config, componentContext: ComponentContext): FilesComponent.Child =
        when (config) {
            is Config.FoldersList -> FilesComponent.Child.ListChild(
                component = foldersListComponent(componentContext, ::onFoldersListOutput)
            )

            is Config.Folder -> FilesComponent.Child.FolderChild(
                component = folderComponent(componentContext, config.folderId, config.folderName, ::onFolderCloseClicked)
            )
        }

    private fun onFoldersListOutput(output: FoldersListComponent.Output) =
        when (output) {
            is FoldersListComponent.Output.Selected -> navigation.push(
                configuration = Config.Folder(
                    folderId = output.folderId,
                    folderName = output.folderName,
                )
            )
        }

    private fun onFolderCloseClicked() = navigation.pop()

    @Serializable
    private sealed class Config {
        @Serializable
        data object FoldersList : Config()

        @Serializable
        data class Folder(
            val folderId: Long,
            val folderName: String,
        ) : Config()
    }
}
