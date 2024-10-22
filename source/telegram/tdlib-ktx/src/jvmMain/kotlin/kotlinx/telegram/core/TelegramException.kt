package kotlinx.telegram.core

import org.drinkless.tdlib.TdApi

sealed class TelegramException(override val message: String) : Throwable(message) {
    object ClientNotAttached :
        TelegramException(
            "Client is not attached. Please call TelegramScope.attachClient() " +
                    "before calling a Telegram function"
        )

    class Error(override val message: String, val code: Int? = null) : TelegramException(message)
    class UnexpectedResult(result: TdApi.Object) : TelegramException("unexpected result: $result")
}
