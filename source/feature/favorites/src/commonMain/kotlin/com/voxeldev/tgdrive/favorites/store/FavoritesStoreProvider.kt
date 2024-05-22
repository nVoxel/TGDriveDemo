package com.voxeldev.tgdrive.favorites.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.voxeldev.tgdrive.favorites.api.FavoriteMessage
import com.voxeldev.tgdrive.favorites.api.GetMessageRequest
import com.voxeldev.tgdrive.favorites.integration.GetAllFavoritesUseCase
import com.voxeldev.tgdrive.favorites.integration.GetMessageFileUseCase
import com.voxeldev.tgdrive.favorites.integration.RemoveSingleFavoriteUseCase
import com.voxeldev.tgdrive.favorites.store.FavoritesStore.Intent
import com.voxeldev.tgdrive.favorites.store.FavoritesStore.State
import com.voxeldev.tgdrive.files.api.file.TGDriveFile
import com.voxeldev.tgdrive.utils.extensions.getMessage
import com.voxeldev.tgdrive.utils.extensions.indexOrNull
import com.voxeldev.tgdrive.utils.integration.BaseUseCase

/**
 * @author nvoxel
 */
class FavoritesStoreProvider(
    private val storeFactory: StoreFactory,
    private val getAllFavoritesUseCase: GetAllFavoritesUseCase = GetAllFavoritesUseCase(),
    private val removeSingleFavoriteUseCase: RemoveSingleFavoriteUseCase = RemoveSingleFavoriteUseCase(),
    private val getMessageFileUseCase: GetMessageFileUseCase = GetMessageFileUseCase(),
) {

    fun provide(): FavoritesStore =
        object :
            FavoritesStore,
            Store<Intent, State, Nothing> by storeFactory.create(
                name = STORE_NAME,
                initialState = State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl,
            ) {}

    private sealed interface Msg {
        data object FavoritesLoading : Msg
        data object FavoritesLoaded : Msg
        data class FavoritesChanged(val favorites: List<FavoriteMessage>) : Msg

        data class FavoriteFileAdded(val tgDriveFile: TGDriveFile) : Msg

        data class Error(val message: String) : Msg
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Unit, State, Msg, Nothing>() {

        override fun executeAction(action: Unit, getState: () -> State) = loadFavorites()

        override fun executeIntent(intent: Intent, getState: () -> State) {
            val state = getState()

            when (intent) {
                is Intent.FileFavoriteClicked -> {
                    val favoritesIndex = state.favorites.indexOrNull { favoriteMessage -> favoriteMessage.id == intent.id } ?: return

                    removeFavorite(
                        favoriteMessage = state.favorites[favoritesIndex],
                    )
                }

                is Intent.Reload -> loadFavorites()
            }
        }

        private fun loadFavorites() {
            dispatch(message = Msg.FavoritesLoading)
            getAllFavoritesUseCase(params = BaseUseCase.NoParams, scope = scope) { result ->
                result.fold(
                    onSuccess = { favoritesFlow ->
                        dispatch(message = Msg.FavoritesLoaded)

                        favoritesFlow.collect { favorites ->
                            dispatch(message = Msg.FavoritesChanged(favorites = favorites))
                            loadFiles(favorites = favorites)
                        }
                    },
                    onFailure = { error(message = it.getMessage()) }
                )
            }
        }

        private fun loadFiles(favorites: List<FavoriteMessage>) {
            favorites.forEach { favoriteMessage ->
                getMessageFileUseCase(
                    params = GetMessageRequest(
                        chatId = favoriteMessage.chatId,
                        messageId = favoriteMessage.messageId,
                    ),
                    scope = scope,
                ) { result ->
                    result.fold(
                        onSuccess = {
                            if (!it.file.isDownloaded()) return@fold
                            dispatch(
                                message = Msg.FavoriteFileAdded(
                                    tgDriveFile = TGDriveFile(
                                        messageFile = it,
                                        favoriteMessage = favoriteMessage,
                                    )
                                )
                            )
                        },
                        onFailure = { error(message = it.getMessage()) }
                    )
                }
            }
        }

        private fun removeFavorite(favoriteMessage: FavoriteMessage) {
            removeSingleFavoriteUseCase(params = favoriteMessage.id, scope = scope) { result ->
                result.onFailure { error(message = it.getMessage()) }
            }
        }

        private fun error(message: String) = dispatch(message = Msg.Error(message = message))
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {
                is Msg.FavoritesLoading -> copy(
                    favorites = mutableListOf(),
                    favoriteFiles = mutableListOf(),
                    isLoading = true,
                    errorText = null,
                )
                is Msg.FavoritesLoaded -> copy(isLoading = false)

                is Msg.FavoritesChanged -> copy(favorites = msg.favorites, favoriteFiles = mutableListOf())

                is Msg.FavoriteFileAdded -> {
                    favoriteFiles.add(msg.tgDriveFile)
                    copy()
                }

                is Msg.Error -> copy(errorText = msg.message, isLoading = false)
            }

    }

    private companion object {
        const val STORE_NAME = "FavoritesStore"
    }
}
