package com.voxeldev.tgdrive.auth.subcomponents.code.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.voxeldev.tgdrive.auth.subcomponents.code.integration.CheckCodeUseCase
import com.voxeldev.tgdrive.auth.subcomponents.code.store.CodeStore.Intent
import com.voxeldev.tgdrive.auth.subcomponents.code.store.CodeStore.State
import com.voxeldev.tgdrive.utils.extensions.getMessage

/**
 * @author nvoxel
 */
internal class CodeStoreProvider(
    private val storeFactory: StoreFactory,
    private val checkCodeUseCase: CheckCodeUseCase = CheckCodeUseCase(),
) {

    fun provide(): CodeStore =
        object :
            CodeStore,
            Store<Intent, State, Nothing> by storeFactory.create(
                name = STORE_NAME,
                initialState = State(),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl,
            ) {}

    private sealed interface Msg {
        data object CodeLoading : Msg
        data object CodeLoaded : Msg
        data class Error(val message: String) : Msg
        data class CodeChanged(val code: String) : Msg
        data object CodeReset : Msg
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Unit, State, Msg, Nothing>() {

        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.Continue -> checkCode(state = getState())
                is Intent.SetCode -> dispatch(message = Msg.CodeChanged(code = intent.code))
                is Intent.Reset -> dispatch(message = Msg.CodeReset)
            }
        }

        private fun checkCode(state: State) {
            dispatch(message = Msg.CodeLoading)
            checkCodeUseCase(params = state.code, scope = scope) { result ->
                result.fold(
                    onSuccess = { dispatch(message = Msg.CodeLoaded) },
                    onFailure = { dispatch(message = Msg.Error(message = it.getMessage())) }
                )
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {

        override fun State.reduce(msg: Msg): State =
            when (msg) {
                is Msg.CodeLoading -> copy(isLoading = true)
                is Msg.CodeLoaded -> copy(isLoading = false)
                is Msg.Error -> copy(errorText = msg.message, isLoading = false)
                is Msg.CodeChanged -> copy(code = msg.code)
                is Msg.CodeReset -> copy(code = "", errorText = null)
            }
    }

    private companion object {
        const val STORE_NAME = "CodeStore"
    }
}
