package com.voxeldev.tgdrive.files.list.integration

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.voxeldev.tgdrive.files.api.platform.LinkHandler
import com.voxeldev.tgdrive.files.list.FoldersListComponent
import com.voxeldev.tgdrive.files.list.FoldersListComponent.Output
import com.voxeldev.tgdrive.files.list.store.FoldersListStore
import com.voxeldev.tgdrive.files.list.store.FoldersListStoreProvider
import com.voxeldev.tgdrive.main.api.chats.ChatInviteLink
import com.voxeldev.tgdrive.utils.extensions.asValue
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

/**
 * @author nvoxel
 */
internal class DefaultFoldersListComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val linkHandler: LinkHandler?,
    private val output: (Output) -> Unit,
) : FoldersListComponent, KoinComponent, ComponentContext by componentContext {

    private val stateMapper: StateMapper = StateMapper()

    private val store = instanceKeeper.getStore {
        FoldersListStoreProvider(
            storeFactory = storeFactory,
            commonTelegramFlow = get(),
        ).provide()
    }

    override val model: Value<FoldersListComponent.Model> = store.asValue().map { state -> stateMapper.toModel(state = state) }

    override fun onFolderClicked(id: Long, name: String) = output(Output.Selected(folderId = id, folderName = name))

    override fun onReloadClicked() = store.accept(intent = FoldersListStore.Intent.Reload)

    override fun onOpenFolderMenuClicked(chatId: Long) = store.accept(intent = FoldersListStore.Intent.OpenFolderMenu(chatId = chatId))
    override fun onDismissFolderMenuClicked() = store.accept(intent = FoldersListStore.Intent.DismissFolderMenu)

    override fun onCreateFolderClicked() = store.accept(intent = FoldersListStore.Intent.CreateFolder)
    override fun onConfirmCreateFolderClicked() = store.accept(intent = FoldersListStore.Intent.ConfirmCreateFolder)
    override fun onDismissCreateFolderClicked() = store.accept(intent = FoldersListStore.Intent.DismissCreateFolder)

    override fun onRenameFolderClicked() = store.accept(intent = FoldersListStore.Intent.RenameFolder)
    override fun onConfirmRenameFolderClicked() = store.accept(intent = FoldersListStore.Intent.ConfirmRenameFolder)
    override fun onDismissRenameFolderClicked() = store.accept(intent = FoldersListStore.Intent.DismissRenameFolder)

    override fun onDeleteFolderClicked() = store.accept(intent = FoldersListStore.Intent.DeleteFolder)
    override fun onConfirmDeleteFolderClicked() = store.accept(intent = FoldersListStore.Intent.ConfirmDeleteFolder)
    override fun onDismissDeleteFolderClicked() = store.accept(intent = FoldersListStore.Intent.DismissDeleteFolder)

    override fun onShareFolderClicked(chatInviteLink: ChatInviteLink) {
        linkHandler?.handleLink(link = chatInviteLink.link)
        store.accept(intent = FoldersListStore.Intent.DismissFolderMenu)
    }

    override fun onFolderTitleUpdate(title: String) = store.accept(intent = FoldersListStore.Intent.SetFolderTitle(title = title))
}
