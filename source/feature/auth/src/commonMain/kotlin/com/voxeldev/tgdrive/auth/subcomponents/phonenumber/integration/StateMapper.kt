package com.voxeldev.tgdrive.auth.subcomponents.phonenumber.integration

import com.voxeldev.tgdrive.auth.subcomponents.phonenumber.PhoneNumberComponent
import com.voxeldev.tgdrive.auth.subcomponents.phonenumber.store.PhoneNumberStore.State

/**
 * @author nvoxel
 */
internal class StateMapper {

    fun toModel(state: State): PhoneNumberComponent.Model =
        PhoneNumberComponent.Model(
            phoneNumber = state.phoneNumber,
            errorText = state.errorText,
            isLoading = state.isLoading,
        )
}
