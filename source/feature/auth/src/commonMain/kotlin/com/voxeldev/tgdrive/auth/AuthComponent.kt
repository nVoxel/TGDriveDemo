package com.voxeldev.tgdrive.auth

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.voxeldev.tgdrive.auth.subcomponents.code.CodeComponent
import com.voxeldev.tgdrive.auth.subcomponents.confirmation.ConfirmationComponent
import com.voxeldev.tgdrive.auth.subcomponents.password.PasswordComponent
import com.voxeldev.tgdrive.auth.subcomponents.phonenumber.PhoneNumberComponent
import com.voxeldev.tgdrive.auth.subcomponents.registration.RegistrationComponent
import com.voxeldev.tgdrive.auth.subcomponents.welcome.WelcomeComponent

/**
 * @author nvoxel
 */
interface AuthComponent {
    val childStack: Value<ChildStack<*, Child>>

    sealed interface Child {
        class CodeChild(val component: CodeComponent) : Child
        class ConfirmationChild(val component: ConfirmationComponent) : Child
        class PasswordChild(val component: PasswordComponent) : Child
        class PhoneNumberChild(val component: PhoneNumberComponent) : Child
        class RegistrationChild(val component: RegistrationComponent) : Child
        class WelcomeChild(val component: WelcomeComponent) : Child
    }

    sealed interface Output {
        data object Success : Output
    }
}
