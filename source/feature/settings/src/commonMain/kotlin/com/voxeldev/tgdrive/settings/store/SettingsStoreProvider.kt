package com.voxeldev.tgdrive.settings.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.voxeldev.tgdrive.settings.SettingsComponent.Output
import com.voxeldev.tgdrive.settings.integration.ClearFavoritesUseCase
import com.voxeldev.tgdrive.settings.integration.LogOutUseCase
import com.voxeldev.tgdrive.settings.store.SettingsStore.Intent
import com.voxeldev.tgdrive.settings.store.SettingsStore.State
import com.voxeldev.tgdrive.utils.extensions.getMessage
import com.voxeldev.tgdrive.utils.integration.BaseUseCase

/**
 * @author nvoxel
 */
internal class SettingsStoreProvider(
    private val storeFactory: StoreFactory,
    private val output: (Output) -> Unit,
    private val logOutUseCase: LogOutUseCase = LogOutUseCase(),
    private val clearFavoritesUseCase: ClearFavoritesUseCase = ClearFavoritesUseCase(),
) {

    fun provide(): SettingsStore =
        object :
        SettingsStore,
            Store<Intent, State, Nothing> by storeFactory.create(
                name = STORE_NAME,
                initialState = State(),
                executorFactory = ::ExecutorImpl,
                reducer = ReducedImpl,
            ) {}

    private sealed interface Msg {
        data object SettingsLoading : Msg
        data object SettingsLoaded : Msg

        data class LogOutDialogVisibilityChanged(val isVisible: Boolean) : Msg

        data class Error(val message: String) : Msg
        data object ErrorCleared : Msg
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Unit, State, Msg, Nothing>() {

        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.ReloadClicked -> dispatch(message = Msg.ErrorCleared)
                is Intent.LogOutClicked -> dispatch(message = Msg.LogOutDialogVisibilityChanged(isVisible = true))
                is Intent.ConfirmLogOut -> logOut()
                is Intent.DismissLogOut -> dispatch(message = Msg.LogOutDialogVisibilityChanged(isVisible = false))
            }
        }

        private fun logOut() {
            dispatch(message = Msg.LogOutDialogVisibilityChanged(isVisible = false))
            dispatch(message = Msg.SettingsLoading)

            logOutUseCase(params = BaseUseCase.NoParams, scope = scope) { result ->
                result.fold(
                    onSuccess = { clearFavorites() },
                    onFailure = { error(message = it.getMessage()) },
                )
            }
        }

        private fun clearFavorites() {
            clearFavoritesUseCase(params = BaseUseCase.NoParams, scope = scope) {
                dispatch(message = Msg.SettingsLoading)
                output(Output.LoggedOut)
            }
        }

        private fun error(message: String) = dispatch(message = Msg.Error(message = message))
    }

    private object ReducedImpl: Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {
                is Msg.SettingsLoading -> copy(isLoading = true, errorText = null)
                is Msg.SettingsLoaded -> copy(isLoading = false)

                is Msg.LogOutDialogVisibilityChanged -> copy(logOutDialogVisible = msg.isVisible)

                is Msg.Error -> copy(errorText = msg.message, isLoading = false)
                is Msg.ErrorCleared -> copy(errorText = null)
            }
    }

    private companion object {
        const val STORE_NAME = "SettingsStore"
    }
}
