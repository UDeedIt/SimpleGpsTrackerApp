package pro.udeedit.demo.simplegpstracker.core.data.network

/**
 * Result of sending a location to the backend.
 *
 * @param statusCode HTTP status code returned by the server.
 * @param body Optional response body as text, if available.
 */
data class LocationSendResult(
    val statusCode: Int,
    val body: String?
)
