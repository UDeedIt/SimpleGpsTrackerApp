package pro.udeedit.demo.simplegpstracker.core.data.network.dto

import kotlinx.serialization.Serializable
import pro.udeedit.demo.simplegpstracker.core.data.network.LocationApi

/**
 * Serializable JSON payload sent to the server for a single location sample.
 *
 * This is the wire format used by [LocationApi] and Ktor.
 *
 * @param deviceId Stable identifier for the user/device.
 * @param userName Optional user-friendly name entered in the app.
 * @param latitude Latitude in decimal degrees.
 * @param longitude Longitude in decimal degrees.
 * @param accuracyMeters Optional accuracy radius in meters, if available.
 * @param timestampMillis Timestamp of the sample in UNIX epoch milliseconds (UTC).
 *
 */
@Serializable
data class LocationPayload(
    /** Stable identifier for the device/user. */
    val deviceId: String,
    /** Optional user-friendly name entered in the app. */
    val userName: String? = null,
    /** Latitude in decimal degrees. */
    val latitude: Double,
    /** Longitude in decimal degrees. */
    val longitude: Double,
    /** Optional accuracy radius in meters, if available. */
    val accuracyMeters: Float? = null,
    /** Timestamp of the sample in UNIX epoch milliseconds (UTC). */
    val timestampMillis: Long,

)
