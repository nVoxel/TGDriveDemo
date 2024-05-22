package kotlinx.telegram.core

import com.voxeldev.tgdrive.auth.api.AuthenticationState
import com.voxeldev.tgdrive.auth.api.TdLibParameters
import com.voxeldev.tgdrive.files.api.channels.Supergroup
import com.voxeldev.tgdrive.main.api.chats.Chat
import com.voxeldev.tgdrive.main.api.files.File
import com.voxeldev.tgdrive.mappers.auth.UpdateAuthorizationStateMapper
import com.voxeldev.tgdrive.mappers.channels.SupergroupMapper
import com.voxeldev.tgdrive.mappers.chats.ChatsMapper
import com.voxeldev.tgdrive.mappers.media.FileMapper
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.telegram.flows.authorizationStateFlow
import kotlinx.telegram.flows.fileFlow
import kotlinx.telegram.flows.newChatFlow
import kotlinx.telegram.flows.supergroupFlow
import org.drinkless.td.libcore.telegram.Client
import org.drinkless.td.libcore.telegram.TdApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.Closeable
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * Main class to interact with Telegram API client
 */
class TelegramFlow(
    val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val flowScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO),
) : CommonTelegramFlow, KoinComponent, Closeable {

    private val updateAuthorizationStateMapper: UpdateAuthorizationStateMapper by inject()
    private val chatsMapper: ChatsMapper by inject()
    private val supergroupMapper: SupergroupMapper by inject()
    private val fileMapper: FileMapper by inject()

    /**
     * Telegram [Client] instance. Null if instance is not attached
     */
    var client: Client? = null

    /**
     * The base Telegram API Flow
     */
    val updateEventsFlow: MutableSharedFlow<TdApi.Object> = MutableSharedFlow(replay = 64)

    /**
     * Attach instance to the existing native Telegram client or create one
     * @param existingClient set an existing client to attach, null by default
     */
    fun attachClient(
        restartClient: Boolean = false,
    ) {
        if (client != null && !restartClient) return

        val resultHandler = Client.ResultHandler {
            flowScope.launch { updateEventsFlow.emit(it) }
        }

        client = Client.create(resultHandler, null, null)
    }

    /**
     * Return data flow from Telegram API of the given type [T]
     */
    inline fun <reified T : TdApi.Object> getUpdatesFlowOfType() =
        updateEventsFlow.filterIsInstance<T>()

    /**
     * Sends a request to the TDLib and expect a result.
     *
     * @param function [TdApi.Function] representing a TDLib interface function-class.
     * @param ExpectedResult result type expecting from given [function].
     * @throws TelegramException.Error if TdApi request returns an exception
     * @throws TelegramException.UnexpectedResult if TdApi request returns an unexpected result
     * @throws TelegramException.ClientNotAttached if TdApi client has not attached yet
     */
    suspend inline fun <reified ExpectedResult : TdApi.Object> sendFunctionAsync(function: TdApi.Function): ExpectedResult =
        withContext(coroutineDispatcher) {
            suspendCancellableCoroutine { continuation ->
                val resultHandler: (TdApi.Object) -> Unit = { result ->
                    when (result) {
                        is ExpectedResult -> continuation.resume(result)
                        is TdApi.Error -> continuation.resumeWithException(
                            TelegramException.Error(result.message, result.code)
                        )

                        else -> continuation.resumeWithException(
                            TelegramException.UnexpectedResult(result)
                        )
                    }
                }
                client?.send(function, resultHandler) { throwable ->
                    continuation.resumeWithException(
                        TelegramException.Error(throwable?.message ?: "unknown")
                    )
                } ?: throw TelegramException.ClientNotAttached
            }
        }

    /**
     * Sends a request to the TDLib and expect [TdApi.Ok]
     *
     * @param function [TdApi.Function] representing a TDLib interface function-class.
     * @throws TelegramException.Error if TdApi request returns an exception
     * @throws TelegramException.UnexpectedResult if TdApi request returns an unexpected result
     * @throws TelegramException.ClientNotAttached if TdApi client has not attached yet
     */
    suspend fun sendFunctionLaunch(function: TdApi.Function) {
        sendFunctionAsync<TdApi.Ok>(function)
    }

    /**
     * Closes Client.
     */
    override fun close() {
        client?.close()
    }

    override fun restartClient() {
        close()
        attachClient(restartClient = true)
    }

    override fun commonAuthorizationStateFlow(): Flow<AuthenticationState> = authorizationStateFlow(
        updateAuthorizationStateMapper = updateAuthorizationStateMapper
    )

    override fun commonSupergroupFlow(): Flow<Supergroup> = supergroupFlow(supergroupMapper = supergroupMapper)

    override fun commonChatFlow(): Flow<Chat> = newChatFlow(chatsMapper = chatsMapper)

    override fun commonFileFlow(): Flow<File> = fileFlow(fileMapper = fileMapper)
}

actual fun getTempTdLibParams(): TdLibParameters =
    TdLibParameters(
        useTestDc = false,
        databaseDirectory = "/data/data/com.voxeldev.tgdrive/files/",
        filesDirectory = "/storage/emulated/0/Documents/TGDrive",
        useFileDatabase = true,
        useChatInfoDatabase = true,
        useMessageDatabase = true,
        useSecretChats = true,
        apiId = 12345,
        apiHash = "apihash",
        systemLanguageCode = "en",
        deviceModel = "Redmi K20",
        systemVersion = null,
        applicationVersion = "android-0.1",
        enableStorageOptimizer = false,
        ignoreFileNames = false,
    )

