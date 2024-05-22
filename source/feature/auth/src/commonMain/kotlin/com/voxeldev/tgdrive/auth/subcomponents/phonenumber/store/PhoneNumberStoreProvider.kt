package com.voxeldev.tgdrive.auth.subcomponents.phonenumber.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.voxeldev.tgdrive.auth.subcomponents.phonenumber.integration.SetPhoneNumberUseCase
import com.voxeldev.tgdrive.auth.subcomponents.phonenumber.store.PhoneNumberStore.Intent
import com.voxeldev.tgdrive.auth.subcomponents.phonenumber.store.PhoneNumberStore.State
import com.voxeldev.tgdrive.utils.extensions.getMessage

/**
 * @author nvoxel
 */
internal class PhoneNumberStoreProvider(
    private val storeFactory: StoreFactory,
    private val setPhoneNumberUseCase: SetPhoneNumberUseCase = SetPhoneNumberUseCase(),
) {

    fun provide(): PhoneNumberStore =
        object :
            PhoneNumberStore,
            Store<Intent, State, Nothing> by storeFactory.create(
                name = STORE_NAME,
                initialState = State(),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl,
            ) {}

    private sealed interface Msg {
        data object PhoneNumberLoading : Msg
        data object PhoneNumberLoaded : Msg
        data class Error(val message: String) : Msg
        data class PhoneNumberChanged(val phoneNumber: String) : Msg
        data object PhoneNumberReset : Msg
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Unit, State, Msg, Nothing>() {

        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.Continue -> setPhoneNumber(state = getState())
                is Intent.SetPhoneNumber -> dispatch(message = Msg.PhoneNumberChanged(phoneNumber = intent.phoneNumber))
                is Intent.Reset -> dispatch(message = Msg.PhoneNumberReset)
            }
        }

        private fun setPhoneNumber(state: State) {
            dispatch(message = Msg.PhoneNumberLoading)
            setPhoneNumberUseCase(params = state.phoneNumber, scope = scope) { result ->
                result.fold(
                    onSuccess = { dispatch(message = Msg.PhoneNumberLoaded) },
                    onFailure = { dispatch(message = Msg.Error(message = it.getMessage())) },
                )
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {

        override fun State.reduce(msg: Msg): State =
            when (msg) {
                is Msg.PhoneNumberLoading -> copy(isLoading = true)
                is Msg.PhoneNumberLoaded -> copy(isLoading = false)
                is Msg.Error -> copy(errorText = msg.message, isLoading = false)
                is Msg.PhoneNumberChanged -> copy(phoneNumber = msg.phoneNumber)
                is Msg.PhoneNumberReset -> copy(phoneNumber = "", errorText = null)
            }
    }

    private companion object {
        const val STORE_NAME = "PhoneNumberStore"
    }
}
