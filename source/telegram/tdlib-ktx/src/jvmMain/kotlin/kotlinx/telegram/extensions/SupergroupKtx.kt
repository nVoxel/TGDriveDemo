//
// NOTE: THIS FILE IS AUTO-GENERATED by the "ExtensionsGenerator".kt
// See: https://github.com/tdlibx/td-ktx-generator/
//
package kotlinx.telegram.extensions

import kotlinx.telegram.core.TelegramFlow
import kotlinx.telegram.coroutines.createSupergroupChat
import kotlinx.telegram.coroutines.disableAllSupergroupUsernames
import kotlinx.telegram.coroutines.getSupergroup
import kotlinx.telegram.coroutines.getSupergroupFullInfo
import kotlinx.telegram.coroutines.getSupergroupMembers
import kotlinx.telegram.coroutines.reorderSupergroupActiveUsernames
import kotlinx.telegram.coroutines.reportSupergroupAntiSpamFalsePositive
import kotlinx.telegram.coroutines.reportSupergroupSpam
import kotlinx.telegram.coroutines.setSupergroupCustomEmojiStickerSet
import kotlinx.telegram.coroutines.setSupergroupStickerSet
import kotlinx.telegram.coroutines.setSupergroupUnrestrictBoostCount
import kotlinx.telegram.coroutines.setSupergroupUsername
import kotlinx.telegram.coroutines.toggleSupergroupHasAggressiveAntiSpamEnabled
import kotlinx.telegram.coroutines.toggleSupergroupHasHiddenMembers
import kotlinx.telegram.coroutines.toggleSupergroupIsAllHistoryAvailable
import kotlinx.telegram.coroutines.toggleSupergroupIsBroadcastGroup
import kotlinx.telegram.coroutines.toggleSupergroupIsForum
import kotlinx.telegram.coroutines.toggleSupergroupJoinByRequest
import kotlinx.telegram.coroutines.toggleSupergroupJoinToSendMessages
import kotlinx.telegram.coroutines.toggleSupergroupSignMessages
import kotlinx.telegram.coroutines.toggleSupergroupUsernameIsActive
import org.drinkless.tdlib.TdApi
import org.drinkless.tdlib.TdApi.Supergroup
import org.drinkless.tdlib.TdApi.SupergroupMembersFilter

/**
 * Interface for access [TdApi.Supergroup] extension functions. Can be used alongside with other
 * extension interfaces of the package. Must contain [TelegramFlow] instance field to access its
 * functionality
 */
interface SupergroupKtx : BaseKtx {
    /**
     * Instance of the [TelegramFlow] connecting extensions to the Telegram Client
     */
    override val api: TelegramFlow

    /**
     * Suspend function, which returns an existing chat corresponding to a known supergroup or
     * channel.
     *
     * @param force Pass true to create the chat without a network request. In this case all
     * information about the chat except its type, title and photo can be incorrect.
     *
     * @return [TdApi.Chat] A chat. (Can be a private chat, basic group, supergroup, or secret chat.)
     */
    suspend fun Supergroup.createChat(force: Boolean) = api.createSupergroupChat(this.id, force)

    /**
     * Suspend function, which disables all active non-editable usernames of a supergroup or channel,
     * requires owner privileges in the supergroup or channel.
     */
    suspend fun Supergroup.disableAllUsernames() = api.disableAllSupergroupUsernames(this.id)

    /**
     * Suspend function, which returns information about a supergroup or a channel by its identifier.
     * This is an offline request if the current user is not a bot.
     *
     *
     * @return [TdApi.Supergroup] Represents a supergroup or channel with zero or more members
     * (subscribers in the case of channels). From the point of view of the system, a channel is a
     * special kind of a supergroup: only administrators can post and see the list of members, and posts
     * from all administrators use the name and photo of the channel instead of individual names and
     * profile photos. Unlike supergroups, channels can have an unlimited number of subscribers.
     */
    suspend fun Supergroup.get() = api.getSupergroup(this.id)

    /**
     * Suspend function, which returns full information about a supergroup or a channel by its
     * identifier, cached for up to 1 minute.
     *
     *
     * @return [TdApi.SupergroupFullInfo] Contains full information about a supergroup or channel.
     */
    suspend fun Supergroup.getFullInfo() = api.getSupergroupFullInfo(this.id)

    /**
     * Suspend function, which returns information about members or banned users in a supergroup or
     * channel. Can be used only if supergroupFullInfo.canGetMembers == true; additionally, administrator
     * privileges may be required for some filters.
     *
     * @param filter The type of users to return; pass null to use supergroupMembersFilterRecent.
     * @param offset Number of users to skip.
     * @param limit The maximum number of users be returned; up to 200.
     *
     * @return [TdApi.ChatMembers] Contains a list of chat members.
     */
    suspend fun Supergroup.getMembers(
        filter: SupergroupMembersFilter?,
        offset: Int,
        limit: Int,
    ) = api.getSupergroupMembers(this.id, filter, offset, limit)

    /**
     * Suspend function, which changes order of active usernames of a supergroup or channel, requires
     * owner privileges in the supergroup or channel.
     *
     * @param usernames The new order of active usernames. All currently active usernames must be
     * specified.
     */
    suspend fun Supergroup.reorderActiveUsernames(usernames: Array<String>?) =
        api.reorderSupergroupActiveUsernames(this.id, usernames)

    /**
     * Suspend function, which reports a false deletion of a message by aggressive anti-spam checks;
     * requires administrator rights in the supergroup. Can be called only for messages from
     * chatEventMessageDeleted with canReportAntiSpamFalsePositive == true.
     *
     * @param messageId Identifier of the erroneously deleted message.
     */
    suspend fun Supergroup.reportAntiSpamFalsePositive(messageId: Long) =
        api.reportSupergroupAntiSpamFalsePositive(this.id, messageId)

    /**
     * Suspend function, which reports messages in a supergroup as spam; requires administrator rights
     * in the supergroup.
     *
     * @param messageIds Identifiers of messages to report.
     */
    suspend fun Supergroup.reportSpam(messageIds: LongArray?) = api.reportSupergroupSpam(
        this.id,
        messageIds
    )

    /**
     * Suspend function, which changes the custom emoji sticker set of a supergroup; requires
     * canChangeInfo administrator right. The chat must have at least
     * chatBoostFeatures.minCustomEmojiStickerSetBoostLevel boost level to pass the corresponding color.
     *
     * @param customEmojiStickerSetId New value of the custom emoji sticker set identifier for the
     * supergroup. Use 0 to remove the custom emoji sticker set in the supergroup.
     */
    suspend fun Supergroup.setCustomEmojiStickerSet(customEmojiStickerSetId: Long) =
        api.setSupergroupCustomEmojiStickerSet(this.id, customEmojiStickerSetId)

    /**
     * Suspend function, which changes the sticker set of a supergroup; requires canChangeInfo
     * administrator right.
     *
     * @param stickerSetId New value of the supergroup sticker set identifier. Use 0 to remove the
     * supergroup sticker set.
     */
    suspend fun Supergroup.setStickerSet(stickerSetId: Long) = api.setSupergroupStickerSet(
        this.id,
        stickerSetId
    )

    /**
     * Suspend function, which changes the number of times the supergroup must be boosted by a user to
     * ignore slow mode and chat permission restrictions; requires canRestrictMembers administrator
     * right.
     *
     * @param unrestrictBoostCount New value of the unrestrictBoostCount supergroup setting; 0-8. Use
     * 0 to remove the setting.
     */
    suspend fun Supergroup.setUnrestrictBoostCount(unrestrictBoostCount: Int) =
        api.setSupergroupUnrestrictBoostCount(this.id, unrestrictBoostCount)

    /**
     * Suspend function, which changes the editable username of a supergroup or channel, requires
     * owner privileges in the supergroup or channel.
     *
     * @param username New value of the username. Use an empty string to remove the username. The
     * username can't be completely removed if there is another active or disabled username.
     */
    suspend fun Supergroup.setUsername(username: String?) = api.setSupergroupUsername(
        this.id,
        username
    )

    /**
     * Suspend function, which toggles whether aggressive anti-spam checks are enabled in the
     * supergroup. Can be called only if supergroupFullInfo.canToggleAggressiveAntiSpam == true.
     *
     * @param hasAggressiveAntiSpamEnabled The new value of hasAggressiveAntiSpamEnabled.
     */
    suspend fun Supergroup.toggleHasAggressiveAntiSpamEnabled(hasAggressiveAntiSpamEnabled: Boolean) =
        api.toggleSupergroupHasAggressiveAntiSpamEnabled(this.id, hasAggressiveAntiSpamEnabled)

    /**
     * Suspend function, which toggles whether non-administrators can receive only administrators and
     * bots using getSupergroupMembers or searchChatMembers. Can be called only if
     * supergroupFullInfo.canHideMembers == true.
     *
     * @param hasHiddenMembers New value of hasHiddenMembers.
     */
    suspend fun Supergroup.toggleHasHiddenMembers(hasHiddenMembers: Boolean) =
        api.toggleSupergroupHasHiddenMembers(this.id, hasHiddenMembers)

    /**
     * Suspend function, which toggles whether the message history of a supergroup is available to new
     * members; requires canChangeInfo member right.
     *
     * @param isAllHistoryAvailable The new value of isAllHistoryAvailable.
     */
    suspend fun Supergroup.toggleIsAllHistoryAvailable(isAllHistoryAvailable: Boolean) =
        api.toggleSupergroupIsAllHistoryAvailable(this.id, isAllHistoryAvailable)

    /**
     * Suspend function, which upgrades supergroup to a broadcast group; requires owner privileges in
     * the supergroup.
     */
    suspend fun Supergroup.toggleIsBroadcastGroup() = api.toggleSupergroupIsBroadcastGroup(this.id)

    /**
     * Suspend function, which toggles whether the supergroup is a forum; requires owner privileges in
     * the supergroup. Discussion supergroups can't be converted to forums.
     *
     * @param isForum New value of isForum.
     */
    suspend fun Supergroup.toggleIsForum(isForum: Boolean) = api.toggleSupergroupIsForum(
        this.id,
        isForum
    )

    /**
     * Suspend function, which toggles whether all users directly joining the supergroup need to be
     * approved by supergroup administrators; requires canRestrictMembers administrator right.
     *
     * @param joinByRequest New value of joinByRequest.
     */
    suspend fun Supergroup.toggleJoinByRequest(joinByRequest: Boolean) =
        api.toggleSupergroupJoinByRequest(this.id, joinByRequest)

    /**
     * Suspend function, which toggles whether joining is mandatory to send messages to a discussion
     * supergroup; requires canRestrictMembers administrator right.
     *
     * @param joinToSendMessages New value of joinToSendMessages.
     */
    suspend fun Supergroup.toggleJoinToSendMessages(joinToSendMessages: Boolean) =
        api.toggleSupergroupJoinToSendMessages(this.id, joinToSendMessages)

    /**
     * Suspend function, which toggles whether sender signature is added to sent messages in a
     * channel; requires canChangeInfo member right.
     *
     * @param signMessages New value of signMessages.
     */
    suspend fun Supergroup.toggleSignMessages(signMessages: Boolean) =
        api.toggleSupergroupSignMessages(this.id, signMessages)

    /**
     * Suspend function, which changes active state for a username of a supergroup or channel,
     * requires owner privileges in the supergroup or channel. The editable username can't be disabled.
     * May return an error with a message &quot;USERNAMES_ACTIVE_TOO_MUCH&quot; if the maximum number of
     * active usernames has been reached.
     *
     * @param username The username to change.
     * @param isActive Pass true to activate the username; pass false to disable it.
     */
    suspend fun Supergroup.toggleUsernameIsActive(username: String?, isActive: Boolean) =
        api.toggleSupergroupUsernameIsActive(this.id, username, isActive)
}
