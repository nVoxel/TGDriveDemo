//
// NOTE: THIS FILE IS AUTO-GENERATED by the "TdApiKtxGenerator".kt
// See: https://github.com/tdlibx/td-ktx-generator/
//
package kotlinx.telegram.coroutines

import kotlinx.telegram.core.TelegramFlow
import org.drinkless.tdlib.TdApi
import org.drinkless.tdlib.TdApi.InputChatPhoto

/**
 * Suspend function, which deletes a profile photo.
 *
 * @param profilePhotoId Identifier of the profile photo to delete.
 */
suspend fun TelegramFlow.deleteProfilePhoto(profilePhotoId: Long) =
    this.sendFunctionLaunch(TdApi.DeleteProfilePhoto(profilePhotoId))

/**
 * Suspend function, which changes a profile photo for a bot.
 *
 * @param botUserId Identifier of the target bot.
 * @param photo Profile photo to set; pass null to delete the chat photo.
 */
suspend fun TelegramFlow.setBotProfilePhoto(botUserId: Long, photo: InputChatPhoto?) =
    this.sendFunctionLaunch(TdApi.SetBotProfilePhoto(botUserId, photo))

/**
 * Suspend function, which changes a profile photo for the current user.
 *
 * @param photo Profile photo to set.
 * @param isPublic Pass true to set a public photo, which will be visible even the main photo is
 * hidden by privacy settings.
 */
suspend fun TelegramFlow.setProfilePhoto(photo: InputChatPhoto?, isPublic: Boolean) =
    this.sendFunctionLaunch(TdApi.SetProfilePhoto(photo, isPublic))