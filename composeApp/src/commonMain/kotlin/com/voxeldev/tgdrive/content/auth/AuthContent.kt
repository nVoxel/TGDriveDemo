package com.voxeldev.tgdrive.content.auth

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.voxeldev.tgdrive.auth.AuthComponent
import com.voxeldev.tgdrive.content.auth.subcomponents.CodeContent
import com.voxeldev.tgdrive.content.auth.subcomponents.ConfirmationContent
import com.voxeldev.tgdrive.content.auth.subcomponents.PasswordContent
import com.voxeldev.tgdrive.content.auth.subcomponents.PhoneNumberContent
import com.voxeldev.tgdrive.content.auth.subcomponents.RegistrationContent
import com.voxeldev.tgdrive.content.auth.subcomponents.WelcomeContent

/**
 * @author nvoxel
 */
@Composable
internal fun AuthContent(component: AuthComponent) {
    Children(
        component = component
    )
}

@Composable
private fun Children(component: AuthComponent, modifier: Modifier = Modifier) {
    Children(
        stack = component.childStack,
        modifier = modifier,
        animation = stackAnimation(fade() + scale()),
    ) {
        when (val child = it.instance) {
            is AuthComponent.Child.CodeChild -> CodeContent(component = child.component)
            is AuthComponent.Child.ConfirmationChild -> ConfirmationContent(component = child.component)
            is AuthComponent.Child.PasswordChild -> PasswordContent(component = child.component)
            is AuthComponent.Child.PhoneNumberChild -> PhoneNumberContent(component = child.component)
            is AuthComponent.Child.RegistrationChild -> RegistrationContent(component = child.component)
            is AuthComponent.Child.WelcomeChild -> WelcomeContent()
        }
    }
}
