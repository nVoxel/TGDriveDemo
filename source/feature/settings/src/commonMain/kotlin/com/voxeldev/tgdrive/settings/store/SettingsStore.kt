package com.voxeldev.tgdrive.settings.store

import com.arkivanov.mvikotlin.core.store.Store
import com.voxeldev.tgdrive.settings.store.SettingsStore.Intent
import com.voxeldev.tgdrive.settings.store.SettingsStore.State

/**
 * @author nvoxel
 */
internal interface SettingsStore : Store<Intent, State, Nothing> {

    sealed interface Intent {
        data object ReloadClicked : Intent
        data object LogOutClicked : Intent
        data object ConfirmLogOut : Intent
        data object DismissLogOut : Intent
    }

    data class State(
        val errorText: String? = null,
        val isLoading: Boolean = false,
        val logOutDialogVisible: Boolean = false,
    )
}
