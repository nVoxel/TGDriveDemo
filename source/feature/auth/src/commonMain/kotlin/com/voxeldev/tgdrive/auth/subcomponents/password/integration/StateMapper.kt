package com.voxeldev.tgdrive.auth.subcomponents.password.integration

import com.voxeldev.tgdrive.auth.subcomponents.password.PasswordComponent
import com.voxeldev.tgdrive.auth.subcomponents.password.store.PasswordStore.State

/**
 * @author nvoxel
 */
internal class StateMapper {

    fun toModel(state: State): PasswordComponent.Model =
        PasswordComponent.Model(
            password = state.password,
            errorText = state.errorText,
            isLoading = state.isLoading,
        )
}
