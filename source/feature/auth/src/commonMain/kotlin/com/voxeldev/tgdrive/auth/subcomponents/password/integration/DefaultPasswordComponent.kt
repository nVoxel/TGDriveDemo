package com.voxeldev.tgdrive.auth.subcomponents.password.integration

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.voxeldev.tgdrive.auth.subcomponents.password.PasswordComponent
import com.voxeldev.tgdrive.auth.subcomponents.password.store.PasswordStore
import com.voxeldev.tgdrive.auth.subcomponents.password.store.PasswordStoreProvider
import com.voxeldev.tgdrive.utils.extensions.asValue

/**
 * @author nvoxel
 */
internal class DefaultPasswordComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
) : PasswordComponent, ComponentContext by componentContext {

    private val stateMapper: StateMapper = StateMapper()

    private val store = instanceKeeper.getStore {
        PasswordStoreProvider(storeFactory = storeFactory).provide()
    }

    override val model: Value<PasswordComponent.Model> = store.asValue().map { state -> stateMapper.toModel(state = state) }

    override fun onPasswordUpdate(password: String) = store.accept(intent = PasswordStore.Intent.SetPassword(password = password))

    override fun onContinueClicked() = store.accept(intent = PasswordStore.Intent.Continue)

    override fun onPasswordReset() = store.accept(intent = PasswordStore.Intent.Reset)
}
