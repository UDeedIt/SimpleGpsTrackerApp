package pro.udeedit.demo.simplegpstracker.core.data.network

import pro.udeedit.demo.simplegpstracker.core.data.network.dto.LocationPayload

/**
 * Abstraction for sending location data to a remote server.
 *
 * Implementations hide the underlying HTTP client and serialization details.
 */
interface LocationApi {

    /**
     * Sends a single [LocationPayload] to the specified [serverUrl].
     *
     * @param serverUrl Full URL of the endpoint that receives location updates.
     * @param apiToken Optional bearer token used for authorization, passed as
     *                 `Authorization: Bearer <token>` when not null.
     * @param payload Location data to send.
     */
    suspend fun sendLocation(
        serverUrl: String,
        apiToken: String?,
        payload: LocationPayload
    )
}
