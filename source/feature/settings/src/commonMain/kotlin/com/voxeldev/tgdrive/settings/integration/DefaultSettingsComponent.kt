package com.voxeldev.tgdrive.settings.integration

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.voxeldev.tgdrive.settings.SettingsComponent
import com.voxeldev.tgdrive.settings.SettingsComponent.Output
import com.voxeldev.tgdrive.settings.store.SettingsStore
import com.voxeldev.tgdrive.settings.store.SettingsStoreProvider
import com.voxeldev.tgdrive.utils.extensions.asValue
import org.koin.core.component.KoinComponent

/**
 * @author nvoxel
 */
class DefaultSettingsComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    output: (Output) -> Unit,
) : SettingsComponent, KoinComponent, ComponentContext by componentContext {

    private val stateMapper: StateMapper = StateMapper()

    private val store = instanceKeeper.getStore {
        SettingsStoreProvider(
            storeFactory = storeFactory,
            output = output,
        ).provide()
    }

    override val model: Value<SettingsComponent.Model> = store.asValue().map { state -> stateMapper.toModel(state = state) }

    override fun onReloadClicked() = store.accept(intent = SettingsStore.Intent.ReloadClicked)

    override fun onLogOutClicked() = store.accept(intent = SettingsStore.Intent.LogOutClicked)
    override fun onConfirmLogOutClicked() = store.accept(intent = SettingsStore.Intent.ConfirmLogOut)
    override fun onDismissLogOutClicked() = store.accept(intent = SettingsStore.Intent.DismissLogOut)
}
