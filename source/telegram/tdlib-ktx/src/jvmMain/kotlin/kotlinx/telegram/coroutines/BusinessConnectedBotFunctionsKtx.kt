//
// NOTE: THIS FILE IS AUTO-GENERATED by the "TdApiKtxGenerator".kt
// See: https://github.com/tdlibx/td-ktx-generator/
//
package kotlinx.telegram.coroutines

import kotlinx.telegram.core.TelegramFlow
import org.drinkless.tdlib.TdApi
import org.drinkless.tdlib.TdApi.BusinessConnectedBot

/**
 * Suspend function, which deletes the business bot that is connected to the current user account.
 *
 * @param botUserId Unique user identifier for the bot.
 */
suspend fun TelegramFlow.deleteBusinessConnectedBot(botUserId: Long) =
    this.sendFunctionLaunch(TdApi.DeleteBusinessConnectedBot(botUserId))

/**
 * Suspend function, which returns the business bot that is connected to the current user account.
 * Returns a 404 error if there is no connected bot.
 *
 * @return [BusinessConnectedBot] Describes a bot connected to a business account.
 */
suspend fun TelegramFlow.getBusinessConnectedBot(): BusinessConnectedBot =
    this.sendFunctionAsync(TdApi.GetBusinessConnectedBot())

/**
 * Suspend function, which adds or changes business bot that is connected to the current user
 * account.
 *
 * @param bot Connection settings for the bot.
 */
suspend fun TelegramFlow.setBusinessConnectedBot(bot: BusinessConnectedBot?) =
    this.sendFunctionLaunch(TdApi.SetBusinessConnectedBot(bot))
