package com.voxeldev.tgdrive.auth.subcomponents.password.store

import com.arkivanov.mvikotlin.core.store.Store
import com.voxeldev.tgdrive.auth.subcomponents.password.store.PasswordStore.Intent
import com.voxeldev.tgdrive.auth.subcomponents.password.store.PasswordStore.State

/**
 * @author nvoxel
 */
internal interface PasswordStore : Store<Intent, State, Nothing> {

    sealed interface Intent {
        data class SetPassword(val password: String) : Intent
        data object Continue : Intent
        data object Reset : Intent
    }

    data class State(
        val password: String = "",
        val errorText: String? = null,
        val isLoading: Boolean = false,
    )
}
