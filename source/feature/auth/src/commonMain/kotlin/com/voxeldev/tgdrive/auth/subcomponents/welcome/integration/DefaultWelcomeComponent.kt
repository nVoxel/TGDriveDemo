package com.voxeldev.tgdrive.auth.subcomponents.welcome.integration

import com.arkivanov.decompose.ComponentContext
import com.voxeldev.tgdrive.auth.subcomponents.welcome.WelcomeComponent

/**
 * @author nvoxel
 */
internal class DefaultWelcomeComponent(
    componentContext: ComponentContext,
) : WelcomeComponent, ComponentContext by componentContext
