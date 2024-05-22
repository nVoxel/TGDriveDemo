//
// NOTE: THIS FILE IS AUTO-GENERATED by the "ExtensionsGenerator".kt
// See: https://github.com/tdlibx/td-ktx-generator/
//
package kotlinx.telegram.extensions

import kotlinx.telegram.core.TelegramFlow
import kotlinx.telegram.coroutines.deleteProfilePhoto
import kotlinx.telegram.coroutines.getRemoteFile
import kotlinx.telegram.coroutines.removeInstalledBackground
import kotlinx.telegram.coroutines.removeNotification
import kotlinx.telegram.coroutines.sendPaymentForm
import kotlinx.telegram.coroutines.setSupergroupStickerSet
import org.drinkless.tdlib.TdApi
import org.drinkless.tdlib.TdApi.Background
import org.drinkless.tdlib.TdApi.FileType
import org.drinkless.tdlib.TdApi.InputCredentials
import org.drinkless.tdlib.TdApi.InputInvoice
import org.drinkless.tdlib.TdApi.Notification
import org.drinkless.tdlib.TdApi.PaymentForm
import org.drinkless.tdlib.TdApi.ProfilePhoto
import org.drinkless.tdlib.TdApi.RemoteFile
import org.drinkless.tdlib.TdApi.ShippingOption
import org.drinkless.tdlib.TdApi.StickerSet

/**
 * Interface for access common
 */
interface CommonKtx : BaseKtx {
    /**
     * Instance of the [TelegramFlow] connecting extensions to the Telegram Client
     */
    override val api: TelegramFlow

    /**
     * Suspend function, which removes background from the list of installed backgrounds.
     */
    suspend fun Background.removeInstalled() = api.removeInstalledBackground(this.id)

    /**
     * Suspend function, which removes an active notification from notification list. Needs to be
     * called only if the notification is removed by the current user.
     *
     * @param notificationGroupId Identifier of notification group to which the notification belongs.
     *
     */
    suspend fun Notification.remove(notificationGroupId: Int) =
        api.removeNotification(notificationGroupId, this.id)

    /**
     * Suspend function, which sends a filled-out payment form to the bot for final verification.
     *
     * @param inputInvoice The invoice.
     * @param orderInfoId Identifier returned by validateOrderInfo, or an empty string.
     * @param shippingOptionId Identifier of a chosen shipping option, if applicable.
     * @param credentials The credentials chosen by user for payment.
     * @param tipAmount Chosen by the user amount of tip in the smallest units of the currency.
     *
     * @return [TdApi.PaymentResult] Contains the result of a payment request.
     */
    suspend fun PaymentForm.send(
        inputInvoice: InputInvoice?,
        orderInfoId: String?,
        shippingOptionId: String?,
        credentials: InputCredentials?,
        tipAmount: Long,
    ) = api.sendPaymentForm(
        inputInvoice, this.id, orderInfoId, shippingOptionId, credentials,
        tipAmount
    )

    /**
     * Suspend function, which deletes a profile photo.
     */
    suspend fun ProfilePhoto.delete() = api.deleteProfilePhoto(this.id)

    /**
     * Suspend function, which returns information about a file by its remote identifier; this is an
     * offline request. Can be used to register a URL as a file for further uploading, or sending as a
     * message. Even the request succeeds, the file can be used only if it is still accessible to the
     * user. For example, if the file is from a message, then the message must be not deleted and
     * accessible to the user. If the file database is disabled, then the corresponding object with the
     * file must be preloaded by the application.
     *
     * @param fileType File type; pass null if unknown.
     *
     * @return [TdApi.File] Represents a file.
     */
    suspend fun RemoteFile.get(fileType: FileType?) = api.getRemoteFile(this.id, fileType)

    /**
     * Suspend function, which sends a filled-out payment form to the bot for final verification.
     *
     * @param inputInvoice The invoice.
     * @param paymentFormId Payment form identifier returned by getPaymentForm.
     * @param orderInfoId Identifier returned by validateOrderInfo, or an empty string.
     * @param credentials The credentials chosen by user for payment.
     * @param tipAmount Chosen by the user amount of tip in the smallest units of the currency.
     *
     * @return [TdApi.PaymentResult] Contains the result of a payment request.
     */
    suspend fun ShippingOption.sendPaymentForm(
        inputInvoice: InputInvoice?,
        paymentFormId: Long,
        orderInfoId: String?,
        credentials: InputCredentials?,
        tipAmount: Long,
    ) = api.sendPaymentForm(inputInvoice, paymentFormId, orderInfoId, this.id, credentials, tipAmount)

    /**
     * Suspend function, which changes the sticker set of a supergroup; requires canChangeInfo
     * administrator right.
     *
     * @param supergroupId Identifier of the supergroup.
     */
    suspend fun StickerSet.setSupergroup(supergroupId: Long) =
        api.setSupergroupStickerSet(supergroupId, this.id)
}
