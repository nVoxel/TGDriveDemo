package com.voxeldev.tgdrive.auth.subcomponents.code.integration

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.voxeldev.tgdrive.auth.subcomponents.code.CodeComponent
import com.voxeldev.tgdrive.auth.subcomponents.code.store.CodeStore
import com.voxeldev.tgdrive.auth.subcomponents.code.store.CodeStoreProvider
import com.voxeldev.tgdrive.utils.extensions.asValue

/**
 * @author nvoxel
 */
internal class DefaultCodeComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
) : CodeComponent, ComponentContext by componentContext {

    private val stateMapper: StateMapper = StateMapper()

    private val store = instanceKeeper.getStore {
        CodeStoreProvider(storeFactory = storeFactory).provide()
    }

    override val model: Value<CodeComponent.Model> = store.asValue().map { state -> stateMapper.toModel(state = state) }

    override fun onCodeUpdate(code: String) = store.accept(intent = CodeStore.Intent.SetCode(code = code))

    override fun onContinueClicked() = store.accept(intent = CodeStore.Intent.Continue)

    override fun onCodeReset() = store.accept(intent = CodeStore.Intent.Reset)
}
