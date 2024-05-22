package com.voxeldev.tgdrive.auth.subcomponents.registration.integration

import com.voxeldev.tgdrive.auth.subcomponents.registration.RegistrationComponent
import com.voxeldev.tgdrive.auth.subcomponents.registration.store.RegistrationStore.State

/**
 * @author nvoxel
 */
internal class StateMapper {

    fun toModel(state: State): RegistrationComponent.Model =
        RegistrationComponent.Model(
            firstName = state.firstName,
            lastName = state.lastName,
            errorText = state.errorText,
            isLoading = state.isLoading,
        )
}
