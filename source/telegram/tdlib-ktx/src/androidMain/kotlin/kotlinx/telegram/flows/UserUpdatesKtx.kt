//
// NOTE: THIS FILE IS AUTO-GENERATED by the "TdApiKtxGenerator".kt
// See: https://github.com/tdlibx/td-ktx-generator/
//
package kotlinx.telegram.flows

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.telegram.core.TelegramFlow
import org.drinkless.td.libcore.telegram.TdApi
import org.drinkless.td.libcore.telegram.TdApi.ChatNearby
import org.drinkless.td.libcore.telegram.TdApi.UpdateUserFullInfo
import org.drinkless.td.libcore.telegram.TdApi.UpdateUserPrivacySettingRules
import org.drinkless.td.libcore.telegram.TdApi.UpdateUserStatus
import org.drinkless.td.libcore.telegram.TdApi.User

/**
 * emits [UpdateUserStatus] if the user went online or offline.
 */
fun TelegramFlow.userStatusFlow(): Flow<UpdateUserStatus> = this.getUpdatesFlowOfType()

/**
 * emits [User] if some data of a user has changed. This update is guaranteed to come before the
 * user identifier is returned to the application.
 */
fun TelegramFlow.userFlow(): Flow<User> = this.getUpdatesFlowOfType<TdApi.UpdateUser>()
    .mapNotNull { it.user }

/**
 * emits [UpdateUserFullInfo] if some data in userFullInfo has been changed.
 */
fun TelegramFlow.userFullInfoFlow(): Flow<UpdateUserFullInfo> = this.getUpdatesFlowOfType()

/**
 * emits [UpdateUserPrivacySettingRules] if some privacy setting rules have been changed.
 */
fun TelegramFlow.userPrivacySettingRulesFlow(): Flow<UpdateUserPrivacySettingRules> =
    this.getUpdatesFlowOfType()

/**
 * emits usersNearby [ChatNearby[]] if the list of users nearby has changed. The update is
 * guaranteed to be sent only 60 seconds after a successful searchChatsNearby request.
 */
fun TelegramFlow.usersNearbyFlow(): Flow<Array<ChatNearby>> =
    this.getUpdatesFlowOfType<TdApi.UpdateUsersNearby>()
        .mapNotNull { it.usersNearby }
