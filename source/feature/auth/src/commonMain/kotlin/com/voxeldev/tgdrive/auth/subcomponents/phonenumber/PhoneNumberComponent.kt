package com.voxeldev.tgdrive.auth.subcomponents.phonenumber

import com.arkivanov.decompose.value.Value

/**
 * @author nvoxel
 */
interface PhoneNumberComponent {

    val model: Value<Model>

    fun onPhoneUpdate(phone: String)

    fun onContinueClicked()

    fun onPhoneReset()

    data class Model(
        val phoneNumber: String,
        val errorText: String?,
        val isLoading: Boolean,
    )
}
