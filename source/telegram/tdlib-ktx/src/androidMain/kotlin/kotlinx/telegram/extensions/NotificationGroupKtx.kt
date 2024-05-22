//
// NOTE: THIS FILE IS AUTO-GENERATED by the "ExtensionsGenerator".kt
// See: https://github.com/tdlibx/td-ktx-generator/
//
package kotlinx.telegram.extensions

import kotlinx.telegram.core.TelegramFlow
import kotlinx.telegram.coroutines.removeNotification
import kotlinx.telegram.coroutines.removeNotificationGroup
import org.drinkless.td.libcore.telegram.TdApi.*

/**
 * Interface for access [TdApi.NotificationGroup] extension functions. Can be used alongside with
 * other extension interfaces of the package. Must contain [TelegramFlow] instance field to access its
 * functionality
 */
interface NotificationGroupKtx : BaseKtx {
    /**
     * Instance of the [TelegramFlow] connecting extensions to the Telegram Client
     */
    override val api: TelegramFlow

    /**
     * Suspend function, which removes an active notification from notification list. Needs to be
     * called only if the notification is removed by the current user.
     *
     * @param notificationId Identifier of removed notification.
     */
    suspend fun NotificationGroup.removeNotification(notificationId: Int) =
        api.removeNotification(this.id, notificationId)

    /**
     * Suspend function, which removes a group of active notifications. Needs to be called only if the
     * notification group is removed by the current user.
     *
     * @param maxNotificationId The maximum identifier of removed notifications.
     */
    suspend fun NotificationGroup.remove(maxNotificationId: Int) =
        api.removeNotificationGroup(this.id, maxNotificationId)
}
