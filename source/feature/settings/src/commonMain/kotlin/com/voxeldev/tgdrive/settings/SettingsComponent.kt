package com.voxeldev.tgdrive.settings

import com.arkivanov.decompose.value.Value

/**
 * @author nvoxel
 */
interface SettingsComponent {

    val model: Value<Model>

    fun onReloadClicked()

    fun onLogOutClicked()
    fun onConfirmLogOutClicked()
    fun onDismissLogOutClicked()

    data class Model(
        val errorText: String?,
        val isLoading: Boolean,
        val logOutDialogVisible: Boolean,
    )

    sealed interface Output {
        data object LoggedOut : Output
    }
}
