//
// NOTE: THIS FILE IS AUTO-GENERATED by the "TdApiKtxGenerator".kt
// See: https://github.com/tdlibx/td-ktx-generator/
//
package kotlinx.telegram.flows

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.telegram.core.TelegramFlow
import org.drinkless.tdlib.TdApi
import org.drinkless.tdlib.TdApi.Story
import org.drinkless.tdlib.TdApi.UpdateStoryDeleted
import org.drinkless.tdlib.TdApi.UpdateStorySendFailed
import org.drinkless.tdlib.TdApi.UpdateStorySendSucceeded
import org.drinkless.tdlib.TdApi.UpdateStoryStealthMode

/**
 * emits [Story] if a story was changed.
 */
fun TelegramFlow.storyFlow(): Flow<Story> = this.getUpdatesFlowOfType<TdApi.UpdateStory>()
    .mapNotNull { it.story }

/**
 * emits [UpdateStoryDeleted] if a story became inaccessible.
 */
fun TelegramFlow.storyDeletedFlow(): Flow<UpdateStoryDeleted> = this.getUpdatesFlowOfType()

/**
 * emits [UpdateStorySendSucceeded] if a story has been successfully sent.
 */
fun TelegramFlow.storySendSucceededFlow(): Flow<UpdateStorySendSucceeded> =
    this.getUpdatesFlowOfType()

/**
 * emits [UpdateStorySendFailed] if a story failed to send. If the story sending is canceled, then
 * updateStoryDeleted will be received instead of this update.
 */
fun TelegramFlow.storySendFailedFlow(): Flow<UpdateStorySendFailed> = this.getUpdatesFlowOfType()

/**
 * emits [UpdateStoryStealthMode] if story stealth mode settings have changed.
 */
fun TelegramFlow.storyStealthModeFlow(): Flow<UpdateStoryStealthMode> = this.getUpdatesFlowOfType()
