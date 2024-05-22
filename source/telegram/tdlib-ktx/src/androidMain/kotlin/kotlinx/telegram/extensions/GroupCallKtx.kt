//
// NOTE: THIS FILE IS AUTO-GENERATED by the "ExtensionsGenerator".kt
// See: https://github.com/tdlibx/td-ktx-generator/
//
package kotlinx.telegram.extensions

import kotlinx.telegram.core.TelegramFlow
import kotlinx.telegram.coroutines.endGroupCall
import kotlinx.telegram.coroutines.endGroupCallRecording
import kotlinx.telegram.coroutines.endGroupCallScreenSharing
import kotlinx.telegram.coroutines.getGroupCall
import kotlinx.telegram.coroutines.getGroupCallInviteLink
import kotlinx.telegram.coroutines.getGroupCallStreamSegment
import kotlinx.telegram.coroutines.inviteGroupCallParticipants
import kotlinx.telegram.coroutines.joinGroupCall
import kotlinx.telegram.coroutines.leaveGroupCall
import kotlinx.telegram.coroutines.loadGroupCallParticipants
import kotlinx.telegram.coroutines.revokeGroupCallInviteLink
import kotlinx.telegram.coroutines.setGroupCallParticipantIsSpeaking
import kotlinx.telegram.coroutines.setGroupCallParticipantVolumeLevel
import kotlinx.telegram.coroutines.setGroupCallTitle
import kotlinx.telegram.coroutines.startGroupCallRecording
import kotlinx.telegram.coroutines.startGroupCallScreenSharing
import kotlinx.telegram.coroutines.startScheduledGroupCall
import kotlinx.telegram.coroutines.toggleGroupCallEnabledStartNotification
import kotlinx.telegram.coroutines.toggleGroupCallIsMyVideoEnabled
import kotlinx.telegram.coroutines.toggleGroupCallIsMyVideoPaused
import kotlinx.telegram.coroutines.toggleGroupCallMuteNewParticipants
import kotlinx.telegram.coroutines.toggleGroupCallParticipantIsHandRaised
import kotlinx.telegram.coroutines.toggleGroupCallParticipantIsMuted
import kotlinx.telegram.coroutines.toggleGroupCallScreenSharingIsPaused
import org.drinkless.td.libcore.telegram.TdApi.*

/**
 * Interface for access [TdApi.GroupCall] extension functions. Can be used alongside with other
 * extension interfaces of the package. Must contain [TelegramFlow] instance field to access its
 * functionality
 */
interface GroupCallKtx : BaseKtx {
    /**
     * Instance of the [TelegramFlow] connecting extensions to the Telegram Client
     */
    override val api: TelegramFlow

    /**
     * Suspend function, which ends a group call. Requires groupCall.canBeManaged.
     */
    suspend fun GroupCall.end() = api.endGroupCall(this.id)

    /**
     * Suspend function, which ends recording of an active group call. Requires groupCall.canBeManaged
     * group call flag.
     */
    suspend fun GroupCall.endRecording() = api.endGroupCallRecording(this.id)

    /**
     * Suspend function, which ends screen sharing in a joined group call.
     */
    suspend fun GroupCall.endScreenSharing() = api.endGroupCallScreenSharing(this.id)

    /**
     * Suspend function, which returns information about a group call.
     *
     *
     * @return [TdApi.GroupCall] Describes a group call.
     */
    suspend fun GroupCall.get() = api.getGroupCall(this.id)

    /**
     * Suspend function, which returns invite link to a video chat in a public chat.
     *
     * @param canSelfUnmute Pass true if the invite link needs to contain an invite hash, passing
     * which to joinGroupCall would allow the invited user to unmute themselves. Requires
     * groupCall.canBeManaged group call flag.
     *
     * @return [TdApi.HttpUrl] Contains an HTTP URL.
     */
    suspend fun GroupCall.getInviteLink(canSelfUnmute: Boolean) = api.getGroupCallInviteLink(
        this.id,
        canSelfUnmute
    )

    /**
     * Suspend function, which returns a file with a segment of a group call stream in a modified OGG
     * format for audio or MPEG-4 format for video.
     *
     * @param timeOffset Point in time when the stream segment begins; Unix timestamp in milliseconds.
     *
     * @param scale Segment duration scale; 0-1. Segment's duration is 1000/(2**scale) milliseconds.
     * @param channelId Identifier of an audio/video channel to get as received from tgcalls.
     * @param videoQuality Video quality as received from tgcalls; pass null to get the worst
     * available quality.
     *
     * @return [TdApi.FilePart] Contains a part of a file.
     */
    suspend fun GroupCall.getStreamSegment(
        timeOffset: Long,
        scale: Int,
        channelId: Int,
        videoQuality: GroupCallVideoQuality?,
    ) = api.getGroupCallStreamSegment(this.id, timeOffset, scale, channelId, videoQuality)

    /**
     * Suspend function, which invites users to an active group call. Sends a service message of type
     * messageInviteToGroupCall for video chats.
     *
     * @param userIds User identifiers. At most 10 users can be invited simultaneously.
     */
    suspend fun GroupCall.inviteParticipants(userIds: LongArray?) =
        api.inviteGroupCallParticipants(this.id, userIds)

    /**
     * Suspend function, which joins an active group call. Returns join response payload for tgcalls.
     *
     * @param participantId Identifier of a group call participant, which will be used to join the
     * call; pass null to join as self; video chats only.
     * @param audioSourceId Caller audio channel synchronization source identifier; received from
     * tgcalls.
     * @param payload Group call join payload; received from tgcalls.
     * @param isMuted True, if the user's microphone is muted.
     * @param isMyVideoEnabled True, if the user's video is enabled.
     * @param inviteHash If non-empty, invite hash to be used to join the group call without being
     * muted by administrators.
     *
     * @return [TdApi.Text] Contains some text.
     */
    suspend fun GroupCall.join(
        participantId: MessageSender?,
        audioSourceId: Int,
        payload: String?,
        isMuted: Boolean,
        isMyVideoEnabled: Boolean,
        inviteHash: String?,
    ) = api.joinGroupCall(
        this.id, participantId, audioSourceId, payload, isMuted, isMyVideoEnabled,
        inviteHash
    )

    /**
     * Suspend function, which leaves a group call.
     */
    suspend fun GroupCall.leave() = api.leaveGroupCall(this.id)

    /**
     * Suspend function, which loads more participants of a group call. The loaded participants will
     * be received through updates. Use the field groupCall.loadedAllParticipants to check whether all
     * participants have already been loaded.
     *
     * @param limit The maximum number of participants to load; up to 100.
     */
    suspend fun GroupCall.loadParticipants(limit: Int) = api.loadGroupCallParticipants(this.id, limit)

    /**
     * Suspend function, which revokes invite link for a group call. Requires groupCall.canBeManaged
     * group call flag.
     */
    suspend fun GroupCall.revokeInviteLink() = api.revokeGroupCallInviteLink(this.id)

    /**
     * Suspend function, which informs TDLib that speaking state of a participant of an active group
     * has changed.
     *
     * @param audioSource Group call participant's synchronization audio source identifier, or 0 for
     * the current user.
     * @param isSpeaking True, if the user is speaking.
     */
    suspend fun GroupCall.setParticipantIsSpeaking(audioSource: Int, isSpeaking: Boolean) =
        api.setGroupCallParticipantIsSpeaking(this.id, audioSource, isSpeaking)

    /**
     * Suspend function, which changes volume level of a participant of an active group call. If the
     * current user can manage the group call, then the participant's volume level will be changed for
     * all users with the default volume level.
     *
     * @param participantId Participant identifier.
     * @param volumeLevel New participant's volume level; 1-20000 in hundreds of percents.
     */
    suspend fun GroupCall.setParticipantVolumeLevel(participantId: MessageSender?, volumeLevel: Int) =
        api.setGroupCallParticipantVolumeLevel(this.id, participantId, volumeLevel)

    /**
     * Suspend function, which sets group call title. Requires groupCall.canBeManaged group call flag.
     *
     * @param title New group call title; 1-64 characters.
     */
    suspend fun GroupCall.setTitle(title: String?) = api.setGroupCallTitle(this.id, title)

    /**
     * Suspend function, which starts recording of an active group call. Requires
     * groupCall.canBeManaged group call flag.
     *
     * @param title Group call recording title; 0-64 characters.
     * @param recordVideo Pass true to record a video file instead of an audio file.
     * @param usePortraitOrientation Pass true to use portrait orientation for video instead of
     * landscape one.
     */
    suspend fun GroupCall.startRecording(
        title: String?,
        recordVideo: Boolean,
        usePortraitOrientation: Boolean,
    ) = api.startGroupCallRecording(this.id, title, recordVideo, usePortraitOrientation)

    /**
     * Suspend function, which starts screen sharing in a joined group call. Returns join response
     * payload for tgcalls.
     *
     * @param audioSourceId Screen sharing audio channel synchronization source identifier; received
     * from tgcalls.
     * @param payload Group call join payload; received from tgcalls.
     *
     * @return [TdApi.Text] Contains some text.
     */
    suspend fun GroupCall.startScreenSharing(audioSourceId: Int, payload: String?) =
        api.startGroupCallScreenSharing(this.id, audioSourceId, payload)

    /**
     * Suspend function, which starts a scheduled group call.
     */
    suspend fun GroupCall.startScheduled() = api.startScheduledGroupCall(this.id)

    /**
     * Suspend function, which toggles whether the current user will receive a notification when the
     * group call will start; scheduled group calls only.
     *
     * @param enabledStartNotification New value of the enabledStartNotification setting.
     */
    suspend fun GroupCall.toggleEnabledStartNotification(enabledStartNotification: Boolean) =
        api.toggleGroupCallEnabledStartNotification(this.id, enabledStartNotification)

    /**
     * Suspend function, which toggles whether current user's video is enabled.
     *
     * @param isMyVideoEnabled Pass true if the current user's video is enabled.
     */
    suspend fun GroupCall.toggleIsMyVideoEnabled(isMyVideoEnabled: Boolean) =
        api.toggleGroupCallIsMyVideoEnabled(this.id, isMyVideoEnabled)

    /**
     * Suspend function, which toggles whether current user's video is paused.
     *
     * @param isMyVideoPaused Pass true if the current user's video is paused.
     */
    suspend fun GroupCall.toggleIsMyVideoPaused(isMyVideoPaused: Boolean) =
        api.toggleGroupCallIsMyVideoPaused(this.id, isMyVideoPaused)

    /**
     * Suspend function, which toggles whether new participants of a group call can be unmuted only by
     * administrators of the group call. Requires groupCall.canToggleMuteNewParticipants group call flag.
     *
     * @param muteNewParticipants New value of the muteNewParticipants setting.
     */
    suspend fun GroupCall.toggleMuteNewParticipants(muteNewParticipants: Boolean) =
        api.toggleGroupCallMuteNewParticipants(this.id, muteNewParticipants)

    /**
     * Suspend function, which toggles whether a group call participant hand is rased.
     *
     * @param participantId Participant identifier.
     * @param isHandRaised Pass true if the user's hand needs to be raised. Only self hand can be
     * raised. Requires groupCall.canBeManaged group call flag to lower other's hand.
     */
    suspend fun GroupCall.toggleParticipantIsHandRaised(
        participantId: MessageSender?,
        isHandRaised: Boolean,
    ) = api.toggleGroupCallParticipantIsHandRaised(
        this.id, participantId,
        isHandRaised
    )

    /**
     * Suspend function, which toggles whether a participant of an active group call is muted,
     * unmuted, or allowed to unmute themselves.
     *
     * @param participantId Participant identifier.
     * @param isMuted Pass true if the user must be muted and false otherwise.
     */
    suspend fun GroupCall.toggleParticipantIsMuted(participantId: MessageSender?, isMuted: Boolean) =
        api.toggleGroupCallParticipantIsMuted(this.id, participantId, isMuted)

    /**
     * Suspend function, which pauses or unpauses screen sharing in a joined group call.
     *
     * @param isPaused True if screen sharing is paused.
     */
    suspend fun GroupCall.toggleScreenSharingIsPaused(isPaused: Boolean) =
        api.toggleGroupCallScreenSharingIsPaused(this.id, isPaused)
}
