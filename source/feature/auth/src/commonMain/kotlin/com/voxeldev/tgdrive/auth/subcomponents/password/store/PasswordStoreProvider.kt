package com.voxeldev.tgdrive.auth.subcomponents.password.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.voxeldev.tgdrive.auth.subcomponents.password.integration.CheckPasswordUseCase
import com.voxeldev.tgdrive.auth.subcomponents.password.store.PasswordStore.Intent
import com.voxeldev.tgdrive.auth.subcomponents.password.store.PasswordStore.State
import com.voxeldev.tgdrive.utils.extensions.getMessage

/**
 * @author nvoxel
 */
internal class PasswordStoreProvider(
    private val storeFactory: StoreFactory,
    private val checkPasswordUseCase: CheckPasswordUseCase = CheckPasswordUseCase(),
) {

    fun provide(): PasswordStore =
        object :
            PasswordStore,
            Store<Intent, State, Nothing> by storeFactory.create(
                name = STORE_NAME,
                initialState = State(),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl,
            ) {}

    private sealed interface Msg {
        data object PasswordLoading : Msg
        data object PasswordLoaded : Msg
        data class Error(val message: String) : Msg
        data class PasswordChanged(val password: String) : Msg
        data object PasswordReset : Msg
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Unit, State, Msg, Nothing>() {

        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.Continue -> checkPassword(state = getState())
                is Intent.SetPassword -> dispatch(message = Msg.PasswordChanged(password = intent.password))
                is Intent.Reset -> dispatch(message = Msg.PasswordReset)
            }
        }

        private fun checkPassword(state: State) {
            dispatch(message = Msg.PasswordLoading)
            checkPasswordUseCase(params = state.password, scope = scope) { result ->
                result.fold(
                    onSuccess = { dispatch(message = Msg.PasswordLoaded) },
                    onFailure = { dispatch(message = Msg.Error(message = it.getMessage())) }
                )
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {

        override fun State.reduce(msg: Msg): State =
            when (msg) {
                is Msg.PasswordLoading -> copy(isLoading = true)
                is Msg.PasswordLoaded -> copy(isLoading = false)
                is Msg.Error -> copy(errorText = msg.message, isLoading = false)
                is Msg.PasswordChanged -> copy(password = msg.password)
                is Msg.PasswordReset -> copy(password = "", errorText = null)
            }
    }

    private companion object {
        const val STORE_NAME = "PasswordStore"
    }
}
