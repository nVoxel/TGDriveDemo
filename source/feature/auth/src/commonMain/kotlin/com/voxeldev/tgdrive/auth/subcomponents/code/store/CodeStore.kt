package com.voxeldev.tgdrive.auth.subcomponents.code.store

import com.arkivanov.mvikotlin.core.store.Store
import com.voxeldev.tgdrive.auth.subcomponents.code.store.CodeStore.Intent
import com.voxeldev.tgdrive.auth.subcomponents.code.store.CodeStore.State

/**
 * @author nvoxel
 */
internal interface CodeStore : Store<Intent, State, Nothing> {

    sealed interface Intent {
        data class SetCode(val code: String) : Intent
        data object Continue : Intent
        data object Reset : Intent
    }

    data class State(
        val code: String = "",
        val errorText: String? = null,
        val isLoading: Boolean = false,
    )
}
