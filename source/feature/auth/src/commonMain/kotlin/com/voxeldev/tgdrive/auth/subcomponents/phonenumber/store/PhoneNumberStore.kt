package com.voxeldev.tgdrive.auth.subcomponents.phonenumber.store

import com.arkivanov.mvikotlin.core.store.Store
import com.voxeldev.tgdrive.auth.subcomponents.phonenumber.store.PhoneNumberStore.Intent
import com.voxeldev.tgdrive.auth.subcomponents.phonenumber.store.PhoneNumberStore.State

/**
 * @author nvoxel
 */
internal interface PhoneNumberStore : Store<Intent, State, Nothing> {

    sealed interface Intent {
        data class SetPhoneNumber(val phoneNumber: String) : Intent
        data object Continue : Intent
        data object Reset : Intent
    }

    data class State(
        val phoneNumber: String = "",
        val errorText: String? = null,
        val isLoading: Boolean = false,
    )
}
