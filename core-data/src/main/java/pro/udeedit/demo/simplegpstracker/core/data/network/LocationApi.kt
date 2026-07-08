package pro.udeedit.demo.simplegpstracker.core.data.network

import pro.udeedit.demo.simplegpstracker.core.data.network.dto.LocationPayload

/**
 * Abstraction for sending location data to a remote server.
 *
 * Implementations hide the underlying HTTP client and serialization details.
 *
 * This interface lives in the `core-data` module, which is pure JVM code.
 * It must not depend on Android APIs (e.g. `android.util.Log`). Any logging
 * related to sending should be done in the Android app module, based on the
 * behavior or result of this call.
 */
interface LocationApi {

    /**
     * Sends a single [LocationPayload] to the specified [serverUrl].
     *
     * @param serverUrl Full URL of the endpoint that receives location updates.
     * @param apiToken Optional bearer token used for authorization, passed as
     *                 `Authorization: Bearer <token>` when not null.
     * @param payload Location data to send.
     *
     * @return [LocationSendResult] containing the HTTP status code and optional body.
     */
    suspend fun sendLocation(
        serverUrl: String,
        apiToken: String?,
        payload: LocationPayload

    ): LocationSendResult
}
