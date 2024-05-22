package com.voxeldev.tgdrive.auth.subcomponents.confirmation

/**
 * @author nvoxel
 */
interface ConfirmationComponent {

    val model: Model

    data class Model(
        val confirmationLink: String,
    )
}
