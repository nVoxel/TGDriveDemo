package com.voxeldev.tgdrive.settings.integration

import com.voxeldev.tgdrive.settings.SettingsComponent
import com.voxeldev.tgdrive.settings.store.SettingsStore.State

/**
 * @author nvoxel
 */
internal class StateMapper {

    fun toModel(state: State): SettingsComponent.Model =
        SettingsComponent.Model(
            errorText = state.errorText,
            isLoading = state.isLoading,
            logOutDialogVisible = state.logOutDialogVisible,
        )
}
