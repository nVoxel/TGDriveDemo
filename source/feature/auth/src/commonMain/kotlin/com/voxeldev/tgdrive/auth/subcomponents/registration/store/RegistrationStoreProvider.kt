package com.voxeldev.tgdrive.auth.subcomponents.registration.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.voxeldev.tgdrive.auth.subcomponents.registration.integration.RegisterUserUseCase
import com.voxeldev.tgdrive.auth.subcomponents.registration.store.RegistrationStore.Intent
import com.voxeldev.tgdrive.auth.subcomponents.registration.store.RegistrationStore.State
import com.voxeldev.tgdrive.utils.extensions.getMessage

/**
 * @author nvoxel
 */
internal class RegistrationStoreProvider(
    private val storeFactory: StoreFactory,
    private val registerUserUseCase: RegisterUserUseCase = RegisterUserUseCase(),
) {

    fun provide(): RegistrationStore =
        object :
            RegistrationStore,
            Store<Intent, State, Nothing> by storeFactory.create(
                name = STORE_NAME,
                initialState = State(),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl,
            ) {}

    private sealed interface Msg {
        data object RegistrationLoading : Msg
        data object RegistrationLoaded : Msg
        data class Error(val message: String) : Msg
        data class FirstNameChanged(val firstName: String) : Msg
        data class LastNameChanged(val lastName: String) : Msg
        data object FieldsReset : Msg
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Unit, State, Msg, Nothing>() {

        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.Continue -> registerUser(state = getState())
                is Intent.SetFirstName -> dispatch(message = Msg.FirstNameChanged(firstName = intent.firstName))
                is Intent.SetLastName -> dispatch(message = Msg.LastNameChanged(lastName = intent.lastName))
                is Intent.Reset -> dispatch(message = Msg.FieldsReset)
            }
        }

        private fun registerUser(state: State) {
            dispatch(message = Msg.RegistrationLoading)
            registerUserUseCase(
                params = state.firstName to state.lastName,
                scope = scope,
            ) { result ->
                result.fold(
                    onSuccess = { dispatch(message = Msg.RegistrationLoaded) },
                    onFailure = { dispatch(message = Msg.Error(message = it.getMessage())) }
                )
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {

        override fun State.reduce(msg: Msg): State =
            when (msg) {
                is Msg.RegistrationLoading -> copy(isLoading = true)
                is Msg.RegistrationLoaded -> copy(isLoading = false)
                is Msg.Error -> copy(errorText = msg.message, isLoading = false)
                is Msg.FirstNameChanged -> copy(firstName = msg.firstName)
                is Msg.LastNameChanged -> copy(lastName = msg.lastName)
                is Msg.FieldsReset -> copy(firstName = "", lastName = "", errorText = null)
            }
    }

    private companion object {
        const val STORE_NAME = "RegistrationStore"
    }
}
