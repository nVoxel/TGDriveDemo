package com.voxeldev.tgdrive.auth.subcomponents.registration

import com.arkivanov.decompose.value.Value

/**
 * @author nvoxel
 */
interface RegistrationComponent {

    val model: Value<Model>

    fun onFirstNameUpdate(firstName: String)

    fun onLastNameUpdate(lastName: String)

    fun onContinueClicked()

    fun onFieldsReset()

    data class Model(
        val firstName: String,
        val lastName: String,
        val errorText: String?,
        val isLoading: Boolean,
    )
}
