@file:Suppress("MemberVisibilityCanBePrivate")

package kotlinx.telegram.core

import com.voxeldev.tgdrive.auth.api.AuthenticationState
import com.voxeldev.tgdrive.auth.api.TdLibParameters
import com.voxeldev.tgdrive.files.api.channels.Supergroup
import com.voxeldev.tgdrive.main.api.chats.Chat
import com.voxeldev.tgdrive.main.api.files.File
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.shareIn


class IosTelegramFlow(
    private val restartClientCallback: () -> Unit,
) : CommonTelegramFlow {

    private val flowScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    lateinit var authenticationCallback: (AuthenticationState) -> Unit
    lateinit var chatCallback: (Chat) -> Unit
    lateinit var supergroupCallback: (Supergroup) -> Unit
    lateinit var fileCallback: (File) -> Unit

    private val authorizationStateFlow =
        callbackFlow {
            authenticationCallback = {
                trySend(it)
            }
            awaitClose {
                // TODO
            }
        }.shareIn(
            scope = flowScope,
            replay = 1,
            started = SharingStarted.Eagerly,
        )

    private val chatFlow =
        callbackFlow {
            chatCallback = {
                trySend(it)
            }
            awaitClose {
                // TODO
            }
        }.shareIn(
            scope = flowScope,
            replay = 1,
            started = SharingStarted.Eagerly,
        )

    private val supergroupFlow =
        callbackFlow {
            supergroupCallback = {
                trySend(it)
            }
            awaitClose {
                // TODO
            }
        }.shareIn(
            scope = flowScope,
            replay = 1,
            started = SharingStarted.Eagerly,
        )

    private val fileFlow =
        callbackFlow {
            fileCallback = {
                trySend(it)
            }
            awaitClose {
                // TODO
            }
        }.shareIn(
            scope = flowScope,
            replay = 1,
            started = SharingStarted.Eagerly,
        )

    override fun restartClient() = restartClientCallback()

    override fun commonAuthorizationStateFlow(): Flow<AuthenticationState> = authorizationStateFlow

    override fun commonChatFlow(): Flow<Chat> = chatFlow

    override fun commonSupergroupFlow(): Flow<Supergroup> = supergroupFlow

    override fun commonFileFlow(): Flow<File> = fileFlow
}

actual fun getTempTdLibParams(): TdLibParameters =
    TdLibParameters(
        useTestDc = false,
        databaseDirectory = "/path/to/database/dir/",
        filesDirectory = "/path/to/files/dir/",
        useFileDatabase = true,
        useChatInfoDatabase = true,
        useMessageDatabase = true,
        useSecretChats = true,
        apiId = 12345,
        apiHash = "apihash",
        systemLanguageCode = "en",
        deviceModel = "iPhone Simulator",
        systemVersion = "17.4",
        applicationVersion = "ios-0.1",
        enableStorageOptimizer = false,
        ignoreFileNames = false,
    )
