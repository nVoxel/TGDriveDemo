package com.voxeldev.tgdrive.auth.subcomponents.registration.store

import com.arkivanov.mvikotlin.core.store.Store
import com.voxeldev.tgdrive.auth.subcomponents.registration.store.RegistrationStore.Intent
import com.voxeldev.tgdrive.auth.subcomponents.registration.store.RegistrationStore.State

/**
 * @author nvoxel
 */
internal interface RegistrationStore : Store<Intent, State, Nothing> {

    sealed interface Intent {
        data class SetFirstName(val firstName: String) : Intent
        data class SetLastName(val lastName: String) : Intent
        data object Continue : Intent
        data object Reset : Intent
    }

    data class State(
        val firstName: String = "",
        val lastName: String = "",
        val errorText: String? = null,
        val isLoading: Boolean = false,
    )
}
