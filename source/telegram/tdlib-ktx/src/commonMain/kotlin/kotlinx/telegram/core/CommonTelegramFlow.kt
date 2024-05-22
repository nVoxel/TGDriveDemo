package kotlinx.telegram.core

import com.voxeldev.tgdrive.auth.api.AuthenticationState
import com.voxeldev.tgdrive.auth.api.TdLibParameters
import com.voxeldev.tgdrive.files.api.channels.Supergroup
import com.voxeldev.tgdrive.main.api.chats.Chat
import com.voxeldev.tgdrive.main.api.files.File
import kotlinx.coroutines.flow.Flow

/**
 * This interface should be used in common code
 * @author nvoxel
 */
interface CommonTelegramFlow {

    fun restartClient()

    fun commonAuthorizationStateFlow(): Flow<AuthenticationState>

    fun commonChatFlow() : Flow<Chat>

    fun commonSupergroupFlow() : Flow<Supergroup>

    fun commonFileFlow() : Flow<File>
}

// todo: remove it
expect fun getTempTdLibParams(): TdLibParameters
