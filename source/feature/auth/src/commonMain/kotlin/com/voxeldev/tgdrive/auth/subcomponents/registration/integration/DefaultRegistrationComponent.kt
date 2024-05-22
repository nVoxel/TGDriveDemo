package com.voxeldev.tgdrive.auth.subcomponents.registration.integration

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.voxeldev.tgdrive.auth.subcomponents.registration.RegistrationComponent
import com.voxeldev.tgdrive.auth.subcomponents.registration.store.RegistrationStore
import com.voxeldev.tgdrive.auth.subcomponents.registration.store.RegistrationStoreProvider
import com.voxeldev.tgdrive.utils.extensions.asValue

/**
 * @author nvoxel
 */
internal class DefaultRegistrationComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
) : RegistrationComponent, ComponentContext by componentContext {

    private val stateMapper: StateMapper = StateMapper()

    private val store = instanceKeeper.getStore {
        RegistrationStoreProvider(storeFactory = storeFactory).provide()
    }

    override val model: Value<RegistrationComponent.Model> = store.asValue().map { state -> stateMapper.toModel(state = state) }

    override fun onFirstNameUpdate(firstName: String) = store.accept(intent = RegistrationStore.Intent.SetFirstName(firstName = firstName))

    override fun onLastNameUpdate(lastName: String) = store.accept(intent = RegistrationStore.Intent.SetLastName(lastName = lastName))

    override fun onContinueClicked() = store.accept(intent = RegistrationStore.Intent.Continue)

    override fun onFieldsReset() = store.accept(intent = RegistrationStore.Intent.Reset)
}
