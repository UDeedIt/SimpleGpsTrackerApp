package pro.udeedit.demo.simplegpstracker.core.data.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import pro.udeedit.demo.simplegpstracker.core.data.network.dto.LocationPayload

/**
 * [LocationApi] implementation backed by the Ktor HTTP client.
 *
 * Uses:
 * - Android engine
 * - Kotlinx Serialization for JSON
 * - Ktor Logging plugin for request/response logging
 */
class KtorLocationApi(

    private val client: HttpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                }
            )
        }

        install(Logging) {
            level = LogLevel.BODY
        }
    }

) : LocationApi {

    /**
     * Sends a [LocationPayload] as JSON via HTTP POST to [serverUrl].
     *
     * If [apiToken] is not null, it is added as a bearer token in the
     * `Authorization` header.
     */
    override suspend fun sendLocation(
        serverUrl: String,
        apiToken: String?,
        payload: LocationPayload
    ) {
        client.post(serverUrl) {
            contentType(ContentType.Application.Json)
            apiToken?.let { header("Authorization", "Bearer $it") }
            setBody(payload)
        }.body<Unit>()
    }
}
