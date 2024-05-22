package com.voxeldev.tgdrive.files.folder.integration

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.voxeldev.tgdrive.files.api.platform.FileProvider
import com.voxeldev.tgdrive.files.api.platform.FileViewer
import com.voxeldev.tgdrive.files.folder.FolderComponent
import com.voxeldev.tgdrive.files.folder.store.FolderStore
import com.voxeldev.tgdrive.files.folder.store.FolderStoreProvider
import com.voxeldev.tgdrive.utils.extensions.asValue
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.telegram.core.CommonTelegramFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.CoroutineContext

/**
 * @author nvoxel
 */
internal class DefaultFolderComponent(
    folderId: Long,
    folderName: String,
    componentContext: ComponentContext,
    mainContext: CoroutineContext,
    storeFactory: StoreFactory,
    fileViewer: FileViewer?,
    private val fileProvider: FileProvider?,
    private val onCloseCallback: () -> Unit,
) : FolderComponent, KoinComponent, ComponentContext by componentContext {

    private val scope = coroutineScope(mainContext + SupervisorJob())

    private val stateMapper: StateMapper = StateMapper(folderName = folderName)
    private val telegramFlow: CommonTelegramFlow by inject()

    private val store = instanceKeeper.getStore {
        FolderStoreProvider(
            folderId = folderId,
            storeFactory = storeFactory,
            fileFlow = telegramFlow.commonFileFlow(),
            fileViewer = fileViewer,
        ).provide()
    }

    init {
        scope.launch {
            fileProvider?.providedFilesFlow?.collect { filePointer ->
                onUploadFileSelected(filePointer = filePointer)
            }
        }
    }

    override val model: Value<FolderComponent.Model> = store.asValue().map { state -> stateMapper.toModel(state = state) }

    override fun onFileClicked(id: Int) = store.accept(intent = FolderStore.Intent.FileClicked(id = id))

    override fun onFileFavoriteClicked(messageId: Long) =
        store.accept(intent = FolderStore.Intent.FileFavoriteClicked(messageId = messageId))

    override fun onReloadClicked() = store.accept(intent = FolderStore.Intent.Reload)

    override fun onUploadsBottomSheetToggle() = store.accept(intent = FolderStore.Intent.UploadsBottomSheetToggle)
    override fun onUploadFileSelect() {
        fileProvider?.onProvide()
    }

    override fun onUploadFileSelected(filePointer: String) = store.accept(intent = FolderStore.Intent.UploadFile(filePointer = filePointer))

    override fun onOpenFileMenuClicked(messageId: Long) = store.accept(intent = FolderStore.Intent.OpenFileMenu(messageId = messageId))
    override fun onDismissFileMenuClicked() = store.accept(intent = FolderStore.Intent.DismissFileMenu)

    override fun onDeleteFileClicked() = store.accept(intent = FolderStore.Intent.DeleteFile)
    override fun onConfirmDeleteFileClicked() = store.accept(intent = FolderStore.Intent.ConfirmDeleteFile)
    override fun onDismissDeleteFileClicked() = store.accept(intent = FolderStore.Intent.DismissDeleteFile)

    override fun onCloseClicked() = onCloseCallback()
}
