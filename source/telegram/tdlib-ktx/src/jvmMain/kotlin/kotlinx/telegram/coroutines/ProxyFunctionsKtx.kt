//
// NOTE: THIS FILE IS AUTO-GENERATED by the "TdApiKtxGenerator".kt
// See: https://github.com/tdlibx/td-ktx-generator/
//
package kotlinx.telegram.coroutines

import kotlinx.telegram.core.TelegramFlow
import org.drinkless.tdlib.TdApi
import org.drinkless.tdlib.TdApi.HttpUrl
import org.drinkless.tdlib.TdApi.Proxy
import org.drinkless.tdlib.TdApi.ProxyType
import org.drinkless.tdlib.TdApi.Seconds

/**
 * Suspend function, which adds a proxy server for network requests. Can be called before
 * authorization.
 *
 * @param server Proxy server domain or IP address.
 * @param port Proxy server port.
 * @param enable Pass true to immediately enable the proxy.
 * @param type Proxy type.
 *
 * @return [Proxy] Contains information about a proxy server.
 */
suspend fun TelegramFlow.addProxy(
    server: String?,
    port: Int,
    enable: Boolean,
    type: ProxyType?,
): Proxy = this.sendFunctionAsync(TdApi.AddProxy(server, port, enable, type))

/**
 * Suspend function, which disables the currently enabled proxy. Can be called before authorization.
 */
suspend fun TelegramFlow.disableProxy() = this.sendFunctionLaunch(TdApi.DisableProxy())

/**
 * Suspend function, which edits an existing proxy server for network requests. Can be called before
 * authorization.
 *
 * @param proxyId Proxy identifier.
 * @param server Proxy server domain or IP address.
 * @param port Proxy server port.
 * @param enable Pass true to immediately enable the proxy.
 * @param type Proxy type.
 *
 * @return [Proxy] Contains information about a proxy server.
 */
suspend fun TelegramFlow.editProxy(
    proxyId: Int,
    server: String?,
    port: Int,
    enable: Boolean,
    type: ProxyType?,
): Proxy = this.sendFunctionAsync(TdApi.EditProxy(proxyId, server, port, enable, type))

/**
 * Suspend function, which enables a proxy. Only one proxy can be enabled at a time. Can be called
 * before authorization.
 *
 * @param proxyId Proxy identifier.
 */
suspend fun TelegramFlow.enableProxy(proxyId: Int) =
    this.sendFunctionLaunch(TdApi.EnableProxy(proxyId))

/**
 * Suspend function, which returns an HTTPS link, which can be used to add a proxy. Available only
 * for SOCKS5 and MTProto proxies. Can be called before authorization.
 *
 * @param proxyId Proxy identifier.
 *
 * @return [HttpUrl] Contains an HTTP URL.
 */
suspend fun TelegramFlow.getProxyLink(proxyId: Int): HttpUrl =
    this.sendFunctionAsync(TdApi.GetProxyLink(proxyId))

/**
 * Suspend function, which computes time needed to receive a response from a Telegram server through
 * a proxy. Can be called before authorization.
 *
 * @param proxyId Proxy identifier. Use 0 to ping a Telegram server without a proxy.
 *
 * @return [Seconds] Contains a value representing a number of seconds.
 */
suspend fun TelegramFlow.pingProxy(proxyId: Int): Seconds =
    this.sendFunctionAsync(TdApi.PingProxy(proxyId))

/**
 * Suspend function, which removes a proxy server. Can be called before authorization.
 *
 * @param proxyId Proxy identifier.
 */
suspend fun TelegramFlow.removeProxy(proxyId: Int) =
    this.sendFunctionLaunch(TdApi.RemoveProxy(proxyId))

/**
 * Suspend function, which sends a simple network request to the Telegram servers via proxy; for
 * testing only. Can be called before authorization.
 *
 * @param server Proxy server domain or IP address.
 * @param port Proxy server port.
 * @param type Proxy type.
 * @param dcId Identifier of a datacenter with which to test connection.
 * @param timeout The maximum overall timeout for the request.
 */
suspend fun TelegramFlow.testProxy(
    server: String?,
    port: Int,
    type: ProxyType?,
    dcId: Int,
    timeout: Double,
) = this.sendFunctionLaunch(TdApi.TestProxy(server, port, type, dcId, timeout))
