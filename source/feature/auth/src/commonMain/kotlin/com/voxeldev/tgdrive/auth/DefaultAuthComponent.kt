package com.voxeldev.tgdrive.auth

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.voxeldev.tgdrive.auth.AuthComponent.Output
import com.voxeldev.tgdrive.auth.api.AuthenticationState
import com.voxeldev.tgdrive.auth.subcomponents.code.CodeComponent
import com.voxeldev.tgdrive.auth.subcomponents.code.integration.DefaultCodeComponent
import com.voxeldev.tgdrive.auth.subcomponents.confirmation.ConfirmationComponent
import com.voxeldev.tgdrive.auth.subcomponents.confirmation.integration.DefaultConfirmationComponent
import com.voxeldev.tgdrive.auth.subcomponents.encryption.CheckDatabaseEncryptionUseCase
import com.voxeldev.tgdrive.auth.subcomponents.password.PasswordComponent
import com.voxeldev.tgdrive.auth.subcomponents.password.integration.DefaultPasswordComponent
import com.voxeldev.tgdrive.auth.subcomponents.phonenumber.PhoneNumberComponent
import com.voxeldev.tgdrive.auth.subcomponents.phonenumber.integration.DefaultPhoneNumberComponent
import com.voxeldev.tgdrive.auth.subcomponents.registration.RegistrationComponent
import com.voxeldev.tgdrive.auth.subcomponents.registration.integration.DefaultRegistrationComponent
import com.voxeldev.tgdrive.auth.subcomponents.welcome.WelcomeComponent
import com.voxeldev.tgdrive.auth.subcomponents.welcome.integration.DefaultWelcomeComponent
import com.voxeldev.tgdrive.auth.subcomponents.welcome.integration.SetTdLibParamsUseCase
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.serializer
import kotlinx.telegram.core.CommonTelegramFlow
import kotlinx.telegram.core.getTempTdLibParams
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.CoroutineContext

/**
 * @author nvoxel
 */
class DefaultAuthComponent internal constructor(
    componentContext: ComponentContext,
    mainContext: CoroutineContext,
    private val setTdLibParamsUseCase: SetTdLibParamsUseCase = SetTdLibParamsUseCase(),
    private val checkDatabaseEncryptionUseCase: CheckDatabaseEncryptionUseCase = CheckDatabaseEncryptionUseCase(),
    private val codeComponent: (ComponentContext) -> CodeComponent,
    private val confirmationComponent: (confirmationLink: String, ComponentContext) -> ConfirmationComponent,
    private val passwordComponent: (ComponentContext) -> PasswordComponent,
    private val phoneNumberComponent: (ComponentContext) -> PhoneNumberComponent,
    private val registrationComponent: (ComponentContext) -> RegistrationComponent,
    private val welcomeComponent: (ComponentContext) -> WelcomeComponent,
) : AuthComponent, KoinComponent, ComponentContext by componentContext {

    private val scope = coroutineScope(mainContext + SupervisorJob())
    private val telegramFlow: CommonTelegramFlow by inject()

    constructor(
        componentContext: ComponentContext,
        storeFactory: StoreFactory,
        mainContext: CoroutineContext,
        output: (Output) -> Unit,
    ) : this(
        componentContext = componentContext,
        mainContext = mainContext,
        codeComponent = { childContext ->
            DefaultCodeComponent(
                componentContext = childContext,
                storeFactory = storeFactory,
            )
        },
        confirmationComponent = { confirmationLink, childContext ->
            DefaultConfirmationComponent(
                componentContext = childContext,
                confirmationLink = confirmationLink,
            )
        },
        passwordComponent = { childContext ->
            DefaultPasswordComponent(
                componentContext = childContext,
                storeFactory = storeFactory,
            )
        },
        phoneNumberComponent = { childContext ->
            DefaultPhoneNumberComponent(
                componentContext = childContext,
                storeFactory = storeFactory,
            )
        },
        registrationComponent = { childContext ->
            DefaultRegistrationComponent(
                componentContext = childContext,
                storeFactory = storeFactory,
            )
        },
        welcomeComponent = { childContext ->
            DefaultWelcomeComponent(
                componentContext = childContext,
            )
        },
    ) {
        scope.launch {
            telegramFlow.commonAuthorizationStateFlow()
                .onEach { state -> setRequiredParams(state = state) }
                .collectLatest { state ->
                    when (state) {
                        is AuthenticationState.WaitPhoneNumber -> navigation.bringToFront(Config.PhoneNumber)
                        is AuthenticationState.WaitCode -> navigation.bringToFront(Config.Code)
                        is AuthenticationState.WaitConfirmation -> navigation.bringToFront(Config.Confirmation(confirmationLink = state.link))
                        is AuthenticationState.WaitPassword -> navigation.bringToFront(Config.Password)
                        is AuthenticationState.WaitRegistration -> navigation.bringToFront(Config.Registration)
                        is AuthenticationState.Ready -> output(Output.Success)
                        is AuthenticationState.Closed -> telegramFlow.restartClient()
                        else -> {}
                    }
                }
        }
    }

    private val navigation = StackNavigation<Config>()

    override val childStack: Value<ChildStack<*, AuthComponent.Child>> =
        childStack(
            source = navigation,
            serializer = serializer(),
            initialConfiguration = Config.Welcome,
            handleBackButton = true,
            childFactory = ::createChild,
        )

    private fun createChild(config: Config, componentContext: ComponentContext): AuthComponent.Child =
        when (config) {
            is Config.Welcome -> AuthComponent.Child.WelcomeChild(
                component = welcomeComponent(componentContext)
            )

            is Config.Code -> AuthComponent.Child.CodeChild(
                component = codeComponent(componentContext)
            )

            is Config.Confirmation -> AuthComponent.Child.ConfirmationChild(
                component = confirmationComponent(config.confirmationLink, componentContext)
            )

            is Config.Password -> AuthComponent.Child.PasswordChild(
                component = passwordComponent(componentContext)
            )

            is Config.PhoneNumber -> AuthComponent.Child.PhoneNumberChild(
                component = phoneNumberComponent(componentContext)
            )

            is Config.Registration -> AuthComponent.Child.RegistrationChild(
                component = registrationComponent(componentContext)
            )
        }

    private fun setRequiredParams(state: AuthenticationState) {
        when (state) {
            is AuthenticationState.WaitTdLibParameters -> setTdLibParamsUseCase(params = getTempTdLibParams(), scope = scope)
            is AuthenticationState.WaitEncryptionKey -> checkDatabaseEncryptionUseCase(params = null, scope = scope)
            else -> {}
        }
    }

    @Serializable
    private sealed class Config {
        @Serializable
        data object Code : Config()

        @Serializable
        data class Confirmation(val confirmationLink: String) : Config()

        @Serializable
        data object Password : Config()

        @Serializable
        data object PhoneNumber : Config()

        @Serializable
        data object Registration : Config()

        @Serializable
        data object Welcome : Config()
    }

}
