//
// NOTE: THIS FILE IS AUTO-GENERATED by the "TdApiKtxGenerator".kt
// See: https://github.com/tdlibx/td-ktx-generator/
//
package kotlinx.telegram.flows

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.telegram.core.TelegramFlow
import org.drinkless.tdlib.TdApi
import org.drinkless.tdlib.TdApi.NotificationGroup
import org.drinkless.tdlib.TdApi.UpdateHavePendingNotifications
import org.drinkless.tdlib.TdApi.UpdateNotification
import org.drinkless.tdlib.TdApi.UpdateNotificationGroup
import org.drinkless.tdlib.TdApi.UpdateScopeNotificationSettings
import org.drinkless.tdlib.TdApi.UpdateServiceNotification

/**
 * emits [UpdateScopeNotificationSettings] if notification settings for some type of chats were
 * updated.
 */
fun TelegramFlow.scopeNotificationSettingsFlow(): Flow<UpdateScopeNotificationSettings> =
    this.getUpdatesFlowOfType()

/**
 * emits [UpdateNotification] if a notification was changed.
 */
fun TelegramFlow.notificationFlow(): Flow<UpdateNotification> = this.getUpdatesFlowOfType()

/**
 * emits [UpdateNotificationGroup] if a list of active notifications in a notification group has
 * changed.
 */
fun TelegramFlow.notificationGroupFlow(): Flow<UpdateNotificationGroup> =
    this.getUpdatesFlowOfType()

/**
 * emits groups [NotificationGroup[]] if contains active notifications that were shown on previous
 * application launches. This update is sent only if the message database is used. In that case it
 * comes once before any updateNotification and updateNotificationGroup update.
 */
fun TelegramFlow.activeNotificationsFlow(): Flow<Array<NotificationGroup>> =
    this.getUpdatesFlowOfType<TdApi.UpdateActiveNotifications>()
        .mapNotNull { it.groups }

/**
 * emits [UpdateHavePendingNotifications] if describes whether there are some pending notification
 * updates. Can be used to prevent application from killing, while there are some pending
 * notifications.
 */
fun TelegramFlow.havePendingNotificationsFlow(): Flow<UpdateHavePendingNotifications> =
    this.getUpdatesFlowOfType()

/**
 * emits [UpdateServiceNotification] if a service notification from the server was received. Upon
 * receiving this the application must show a popup with the content of the notification.
 */
fun TelegramFlow.serviceNotificationFlow(): Flow<UpdateServiceNotification> =
    this.getUpdatesFlowOfType()

/**
 * emits notificationSoundIds [Long[]] if the list of saved notification sounds was updated. This
 * update may not be sent until information about a notification sound was requested for the first
 * time.
 */
fun TelegramFlow.savedNotificationSoundsFlow(): Flow<LongArray> =
    this.getUpdatesFlowOfType<TdApi.UpdateSavedNotificationSounds>()
        .mapNotNull { it.notificationSoundIds }