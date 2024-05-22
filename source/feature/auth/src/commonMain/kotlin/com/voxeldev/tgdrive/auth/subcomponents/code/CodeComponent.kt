package com.voxeldev.tgdrive.auth.subcomponents.code

import com.arkivanov.decompose.value.Value

/**
 * @author nvoxel
 */
interface CodeComponent {

    val model: Value<Model>

    fun onCodeUpdate(code: String)

    fun onContinueClicked()

    fun onCodeReset()

    data class Model(
        val code: String,
        val errorText: String?,
        val isLoading: Boolean,
    )
}
