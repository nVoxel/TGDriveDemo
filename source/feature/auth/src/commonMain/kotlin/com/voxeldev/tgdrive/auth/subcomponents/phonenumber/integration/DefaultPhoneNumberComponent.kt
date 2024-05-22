package com.voxeldev.tgdrive.auth.subcomponents.phonenumber.integration

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.voxeldev.tgdrive.auth.subcomponents.phonenumber.PhoneNumberComponent
import com.voxeldev.tgdrive.auth.subcomponents.phonenumber.store.PhoneNumberStore
import com.voxeldev.tgdrive.auth.subcomponents.phonenumber.store.PhoneNumberStoreProvider
import com.voxeldev.tgdrive.utils.extensions.asValue

/**
 * @author nvoxel
 */
internal class DefaultPhoneNumberComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
) : PhoneNumberComponent, ComponentContext by componentContext {

    private val stateMapper: StateMapper = StateMapper()

    private val store = instanceKeeper.getStore {
        PhoneNumberStoreProvider(storeFactory = storeFactory).provide()
    }

    override val model: Value<PhoneNumberComponent.Model> = store.asValue().map { state -> stateMapper.toModel(state = state) }

    override fun onPhoneUpdate(phone: String) = store.accept(intent = PhoneNumberStore.Intent.SetPhoneNumber(phoneNumber = phone))

    override fun onContinueClicked() = store.accept(intent = PhoneNumberStore.Intent.Continue)

    override fun onPhoneReset() = store.accept(intent = PhoneNumberStore.Intent.Reset)
}
