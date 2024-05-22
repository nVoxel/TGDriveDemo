package com.voxeldev.tgdrive.auth.subcomponents.code.integration

import com.voxeldev.tgdrive.auth.subcomponents.code.CodeComponent
import com.voxeldev.tgdrive.auth.subcomponents.code.store.CodeStore.State

/**
 * @author nvoxel
 */
internal class StateMapper {

    fun toModel(state: State): CodeComponent.Model =
        CodeComponent.Model(
            code = state.code,
            errorText = state.errorText,
            isLoading = state.isLoading,
        )
}
