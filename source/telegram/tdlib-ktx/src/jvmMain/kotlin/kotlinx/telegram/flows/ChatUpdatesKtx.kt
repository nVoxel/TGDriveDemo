//
// NOTE: THIS FILE IS AUTO-GENERATED by the "TdApiKtxGenerator".kt
// See: https://github.com/tdlibx/td-ktx-generator/
//
package kotlinx.telegram.flows

import com.voxeldev.tgdrive.main.api.chats.Chat
import com.voxeldev.tgdrive.mappers.chats.ChatsMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.telegram.core.TelegramFlow
import org.drinkless.tdlib.TdApi
import org.drinkless.tdlib.TdApi.ChatActiveStories
import org.drinkless.tdlib.TdApi.ChatTheme
import org.drinkless.tdlib.TdApi.SecretChat
import org.drinkless.tdlib.TdApi.UpdateAddChatMembersPrivacyForbidden
import org.drinkless.tdlib.TdApi.UpdateChatAccentColors
import org.drinkless.tdlib.TdApi.UpdateChatAction
import org.drinkless.tdlib.TdApi.UpdateChatActionBar
import org.drinkless.tdlib.TdApi.UpdateChatAddedToList
import org.drinkless.tdlib.TdApi.UpdateChatAvailableReactions
import org.drinkless.tdlib.TdApi.UpdateChatBackground
import org.drinkless.tdlib.TdApi.UpdateChatBlockList
import org.drinkless.tdlib.TdApi.UpdateChatBoost
import org.drinkless.tdlib.TdApi.UpdateChatDefaultDisableNotification
import org.drinkless.tdlib.TdApi.UpdateChatDraftMessage
import org.drinkless.tdlib.TdApi.UpdateChatEmojiStatus
import org.drinkless.tdlib.TdApi.UpdateChatFolders
import org.drinkless.tdlib.TdApi.UpdateChatHasProtectedContent
import org.drinkless.tdlib.TdApi.UpdateChatHasScheduledMessages
import org.drinkless.tdlib.TdApi.UpdateChatIsMarkedAsUnread
import org.drinkless.tdlib.TdApi.UpdateChatIsTranslatable
import org.drinkless.tdlib.TdApi.UpdateChatLastMessage
import org.drinkless.tdlib.TdApi.UpdateChatMember
import org.drinkless.tdlib.TdApi.UpdateChatMessageAutoDeleteTime
import org.drinkless.tdlib.TdApi.UpdateChatMessageSender
import org.drinkless.tdlib.TdApi.UpdateChatNotificationSettings
import org.drinkless.tdlib.TdApi.UpdateChatOnlineMemberCount
import org.drinkless.tdlib.TdApi.UpdateChatPendingJoinRequests
import org.drinkless.tdlib.TdApi.UpdateChatPermissions
import org.drinkless.tdlib.TdApi.UpdateChatPhoto
import org.drinkless.tdlib.TdApi.UpdateChatPosition
import org.drinkless.tdlib.TdApi.UpdateChatReadInbox
import org.drinkless.tdlib.TdApi.UpdateChatReadOutbox
import org.drinkless.tdlib.TdApi.UpdateChatRemovedFromList
import org.drinkless.tdlib.TdApi.UpdateChatReplyMarkup
import org.drinkless.tdlib.TdApi.UpdateChatTheme
import org.drinkless.tdlib.TdApi.UpdateChatTitle
import org.drinkless.tdlib.TdApi.UpdateChatUnreadMentionCount
import org.drinkless.tdlib.TdApi.UpdateChatUnreadReactionCount
import org.drinkless.tdlib.TdApi.UpdateChatVideoChat
import org.drinkless.tdlib.TdApi.UpdateChatViewAsTopics
import org.drinkless.tdlib.TdApi.UpdateNewChatJoinRequest
import org.drinkless.tdlib.TdApi.UpdateStoryListChatCount
import org.drinkless.tdlib.TdApi.UpdateUnreadChatCount

/**
 * emits [Chat] if a new chat has been loaded/created. This update is guaranteed to come before the
 * chat identifier is returned to the application. The chat field changes will be reported through
 * separate updates.
 */
fun TelegramFlow.newChatFlow(chatsMapper: ChatsMapper): Flow<Chat> = this.getUpdatesFlowOfType<TdApi.UpdateNewChat>()
    .mapNotNull { chatsMapper.toResponse(chat = it.chat) }

/**
 * emits [UpdateChatTitle] if the title of a chat was changed.
 */
fun TelegramFlow.chatTitleFlow(): Flow<UpdateChatTitle> = this.getUpdatesFlowOfType()

/**
 * emits [UpdateChatPhoto] if a chat photo was changed.
 */
fun TelegramFlow.chatPhotoFlow(): Flow<UpdateChatPhoto> = this.getUpdatesFlowOfType()

/**
 * emits [UpdateChatAccentColors] if chat accent colors have changed.
 */
fun TelegramFlow.chatAccentColorsFlow(): Flow<UpdateChatAccentColors> = this.getUpdatesFlowOfType()

/**
 * emits [UpdateChatPermissions] if chat permissions were changed.
 */
fun TelegramFlow.chatPermissionsFlow(): Flow<UpdateChatPermissions> = this.getUpdatesFlowOfType()

/**
 * emits [UpdateChatLastMessage] if the last message of a chat was changed.
 */
fun TelegramFlow.chatLastMessageFlow(): Flow<UpdateChatLastMessage> = this.getUpdatesFlowOfType()

/**
 * emits [UpdateChatPosition] if the position of a chat in a chat list has changed. An
 * updateChatLastMessage or updateChatDraftMessage update might be sent instead of the update.
 */
fun TelegramFlow.chatPositionFlow(): Flow<UpdateChatPosition> = this.getUpdatesFlowOfType()

/**
 * emits [UpdateChatAddedToList] if a chat was added to a chat list.
 */
fun TelegramFlow.chatAddedToListFlow(): Flow<UpdateChatAddedToList> = this.getUpdatesFlowOfType()

/**
 * emits [UpdateChatRemovedFromList] if a chat was removed from a chat list.
 */
fun TelegramFlow.chatRemovedFromListFlow(): Flow<UpdateChatRemovedFromList> =
    this.getUpdatesFlowOfType()

/**
 * emits [UpdateChatReadInbox] if incoming messages were read or the number of unread messages has
 * been changed.
 */
fun TelegramFlow.chatReadInboxFlow(): Flow<UpdateChatReadInbox> = this.getUpdatesFlowOfType()

/**
 * emits [UpdateChatReadOutbox] if outgoing messages were read.
 */
fun TelegramFlow.chatReadOutboxFlow(): Flow<UpdateChatReadOutbox> = this.getUpdatesFlowOfType()

/**
 * emits [UpdateChatActionBar] if the chat action bar was changed.
 */
fun TelegramFlow.chatActionBarFlow(): Flow<UpdateChatActionBar> = this.getUpdatesFlowOfType()

/**
 * emits [UpdateChatAvailableReactions] if the chat available reactions were changed.
 */
fun TelegramFlow.chatAvailableReactionsFlow(): Flow<UpdateChatAvailableReactions> =
    this.getUpdatesFlowOfType()

/**
 * emits [UpdateChatDraftMessage] if a chat draft has changed. Be aware that the update may come in
 * the currently opened chat but with old content of the draft. If the user has changed the content of
 * the draft, this update mustn't be applied.
 */
fun TelegramFlow.chatDraftMessageFlow(): Flow<UpdateChatDraftMessage> = this.getUpdatesFlowOfType()

/**
 * emits [UpdateChatEmojiStatus] if chat emoji status has changed.
 */
fun TelegramFlow.chatEmojiStatusFlow(): Flow<UpdateChatEmojiStatus> = this.getUpdatesFlowOfType()

/**
 * emits [UpdateChatMessageSender] if the message sender that is selected to send messages in a chat
 * has changed.
 */
fun TelegramFlow.chatMessageSenderFlow(): Flow<UpdateChatMessageSender> =
    this.getUpdatesFlowOfType()

/**
 * emits [UpdateChatMessageAutoDeleteTime] if the message auto-delete or self-destruct timer setting
 * for a chat was changed.
 */
fun TelegramFlow.chatMessageAutoDeleteTimeFlow(): Flow<UpdateChatMessageAutoDeleteTime> =
    this.getUpdatesFlowOfType()

/**
 * emits [UpdateChatNotificationSettings] if notification settings for a chat were changed.
 */
fun TelegramFlow.chatNotificationSettingsFlow(): Flow<UpdateChatNotificationSettings> =
    this.getUpdatesFlowOfType()

/**
 * emits [UpdateChatPendingJoinRequests] if the chat pending join requests were changed.
 */
fun TelegramFlow.chatPendingJoinRequestsFlow(): Flow<UpdateChatPendingJoinRequests> =
    this.getUpdatesFlowOfType()

/**
 * emits [UpdateChatReplyMarkup] if the default chat reply markup was changed. Can occur because new
 * messages with reply markup were received or because an old reply markup was hidden by the user.
 */
fun TelegramFlow.chatReplyMarkupFlow(): Flow<UpdateChatReplyMarkup> = this.getUpdatesFlowOfType()

/**
 * emits [UpdateChatBackground] if the chat background was changed.
 */
fun TelegramFlow.chatBackgroundFlow(): Flow<UpdateChatBackground> = this.getUpdatesFlowOfType()

/**
 * emits [UpdateChatTheme] if the chat theme was changed.
 */
fun TelegramFlow.chatThemeFlow(): Flow<UpdateChatTheme> = this.getUpdatesFlowOfType()

/**
 * emits [UpdateChatUnreadMentionCount] if the chat unreadMentionCount has changed.
 */
fun TelegramFlow.chatUnreadMentionCountFlow(): Flow<UpdateChatUnreadMentionCount> =
    this.getUpdatesFlowOfType()

/**
 * emits [UpdateChatUnreadReactionCount] if the chat unreadReactionCount has changed.
 */
fun TelegramFlow.chatUnreadReactionCountFlow(): Flow<UpdateChatUnreadReactionCount> =
    this.getUpdatesFlowOfType()

/**
 * emits [UpdateChatVideoChat] if a chat video chat state has changed.
 */
fun TelegramFlow.chatVideoChatFlow(): Flow<UpdateChatVideoChat> = this.getUpdatesFlowOfType()

/**
 * emits [UpdateChatDefaultDisableNotification] if the value of the default disableNotification
 * parameter, used when a message is sent to the chat, was changed.
 */
fun TelegramFlow.chatDefaultDisableNotificationFlow(): Flow<UpdateChatDefaultDisableNotification> =
    this.getUpdatesFlowOfType()

/**
 * emits [UpdateChatHasProtectedContent] if a chat content was allowed or restricted for saving.
 */
fun TelegramFlow.chatHasProtectedContentFlow(): Flow<UpdateChatHasProtectedContent> =
    this.getUpdatesFlowOfType()

/**
 * emits [UpdateChatIsTranslatable] if translation of chat messages was enabled or disabled.
 */
fun TelegramFlow.chatIsTranslatableFlow(): Flow<UpdateChatIsTranslatable> =
    this.getUpdatesFlowOfType()

/**
 * emits [UpdateChatIsMarkedAsUnread] if a chat was marked as unread or was read.
 */
fun TelegramFlow.chatIsMarkedAsUnreadFlow(): Flow<UpdateChatIsMarkedAsUnread> =
    this.getUpdatesFlowOfType()

/**
 * emits [UpdateChatViewAsTopics] if a chat default appearance has changed.
 */
fun TelegramFlow.chatViewAsTopicsFlow(): Flow<UpdateChatViewAsTopics> = this.getUpdatesFlowOfType()

/**
 * emits [UpdateChatBlockList] if a chat was blocked or unblocked.
 */
fun TelegramFlow.chatBlockListFlow(): Flow<UpdateChatBlockList> = this.getUpdatesFlowOfType()

/**
 * emits [UpdateChatHasScheduledMessages] if a chat's hasScheduledMessages field has changed.
 */
fun TelegramFlow.chatHasScheduledMessagesFlow(): Flow<UpdateChatHasScheduledMessages> =
    this.getUpdatesFlowOfType()

/**
 * emits [UpdateChatFolders] if the list of chat folders or a chat folder has changed.
 */
fun TelegramFlow.chatFoldersFlow(): Flow<UpdateChatFolders> = this.getUpdatesFlowOfType()

/**
 * emits [UpdateChatOnlineMemberCount] if the number of online group members has changed. This
 * update with non-zero number of online group members is sent only for currently opened chats. There
 * is no guarantee that it is sent just after the number of online users has changed.
 */
fun TelegramFlow.chatOnlineMemberCountFlow(): Flow<UpdateChatOnlineMemberCount> =
    this.getUpdatesFlowOfType()

/**
 * emits [UpdateChatAction] if a message sender activity in the chat has changed.
 */
fun TelegramFlow.chatActionFlow(): Flow<UpdateChatAction> = this.getUpdatesFlowOfType()

/**
 * emits [SecretChat] if some data of a secret chat has changed. This update is guaranteed to come
 * before the secret chat identifier is returned to the application.
 */
fun TelegramFlow.secretChatFlow(): Flow<SecretChat> =
    this.getUpdatesFlowOfType<TdApi.UpdateSecretChat>()
        .mapNotNull { it.secretChat }

/**
 * emits [UpdateUnreadChatCount] if number of unread chats, i.e. with unread messages or marked as
 * unread, has changed. This update is sent only if the message database is used.
 */
fun TelegramFlow.unreadChatCountFlow(): Flow<UpdateUnreadChatCount> = this.getUpdatesFlowOfType()

/**
 * emits activeStories [ChatActiveStories] if the list of active stories posted by a specific chat
 * has changed.
 */
fun TelegramFlow.chatActiveStoriesFlow(): Flow<ChatActiveStories> =
    this.getUpdatesFlowOfType<TdApi.UpdateChatActiveStories>()
        .mapNotNull { it.activeStories }

/**
 * emits [UpdateStoryListChatCount] if number of chats in a story list has changed.
 */
fun TelegramFlow.storyListChatCountFlow(): Flow<UpdateStoryListChatCount> =
    this.getUpdatesFlowOfType()

/**
 * emits chatThemes [ChatTheme[]] if the list of available chat themes has changed.
 */
fun TelegramFlow.chatThemesFlow(): Flow<Array<ChatTheme>> =
    this.getUpdatesFlowOfType<TdApi.UpdateChatThemes>()
        .mapNotNull { it.chatThemes }

/**
 * emits [UpdateAddChatMembersPrivacyForbidden] if adding users to a chat has failed because of
 * their privacy settings. An invite link can be shared with the users if appropriate.
 */
fun TelegramFlow.addChatMembersPrivacyForbiddenFlow(): Flow<UpdateAddChatMembersPrivacyForbidden> =
    this.getUpdatesFlowOfType()

/**
 * emits [UpdateChatMember] if user rights changed in a chat; for bots only.
 */
fun TelegramFlow.chatMemberFlow(): Flow<UpdateChatMember> = this.getUpdatesFlowOfType()

/**
 * emits [UpdateNewChatJoinRequest] if a user sent a join request to a chat; for bots only.
 */
fun TelegramFlow.newChatJoinRequestFlow(): Flow<UpdateNewChatJoinRequest> =
    this.getUpdatesFlowOfType()

/**
 * emits [UpdateChatBoost] if a chat boost has changed; for bots only.
 */
fun TelegramFlow.chatBoostFlow(): Flow<UpdateChatBoost> = this.getUpdatesFlowOfType()
