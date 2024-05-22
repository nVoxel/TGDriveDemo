//
// NOTE: THIS FILE IS AUTO-GENERATED by the "ExtensionsGenerator".kt
// See: https://github.com/tdlibx/td-ktx-generator/
//
package kotlinx.telegram.extensions

import kotlinx.telegram.core.TelegramFlow
import kotlinx.telegram.coroutines.closeSecretChat
import kotlinx.telegram.coroutines.createSecretChat
import kotlinx.telegram.coroutines.getSecretChat
import org.drinkless.tdlib.TdApi
import org.drinkless.tdlib.TdApi.SecretChat

/**
 * Interface for access [TdApi.SecretChat] extension functions. Can be used alongside with other
 * extension interfaces of the package. Must contain [TelegramFlow] instance field to access its
 * functionality
 */
interface SecretChatKtx : BaseKtx {
    /**
     * Instance of the [TelegramFlow] connecting extensions to the Telegram Client
     */
    override val api: TelegramFlow

    /**
     * Suspend function, which closes a secret chat, effectively transferring its state to
     * secretChatStateClosed.
     */
    suspend fun SecretChat.close() = api.closeSecretChat(this.id)

    /**
     * Suspend function, which returns an existing chat corresponding to a known secret chat.
     *
     *
     * @return [TdApi.Chat] A chat. (Can be a private chat, basic group, supergroup, or secret chat.)
     */
    suspend fun SecretChat.create() = api.createSecretChat(this.id)

    /**
     * Suspend function, which returns information about a secret chat by its identifier. This is an
     * offline request.
     *
     *
     * @return [TdApi.SecretChat] Represents a secret chat.
     */
    suspend fun SecretChat.get() = api.getSecretChat(this.id)
}
