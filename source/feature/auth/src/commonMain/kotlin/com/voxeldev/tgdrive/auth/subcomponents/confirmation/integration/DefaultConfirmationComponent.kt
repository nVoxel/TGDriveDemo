package com.voxeldev.tgdrive.auth.subcomponents.confirmation.integration

import com.arkivanov.decompose.ComponentContext
import com.voxeldev.tgdrive.auth.subcomponents.confirmation.ConfirmationComponent
import com.voxeldev.tgdrive.auth.subcomponents.confirmation.ConfirmationComponent.Model

/**
 * @author nvoxel
 */
internal class DefaultConfirmationComponent(
    componentContext: ComponentContext,
    confirmationLink: String,
) : ConfirmationComponent, ComponentContext by componentContext {

    override val model: Model = Model(
        confirmationLink = confirmationLink,
    )
}
