package com.voxeldev.tgdrive.auth.subcomponents.password

import com.arkivanov.decompose.value.Value

/**
 * @author nvoxel
 */
interface PasswordComponent {

    val model: Value<Model>

    fun onPasswordUpdate(password: String)

    fun onContinueClicked()

    fun onPasswordReset()

    data class Model(
        val password: String,
        val errorText: String?,
        val isLoading: Boolean,
    )
}
