package pro.udeedit.demo.simplegpstracker.core.data.network.dto

import kotlinx.serialization.Serializable
import pro.udeedit.demo.simplegpstracker.core.data.network.LocationApi

/**
 * Serializable payload sent to the server for a single location sample.
 *
 * This is the wire format used by [LocationApi] and Ktor.
 */
@Serializable
data class LocationPayload(
    /** Stable identifier for the device/user. */
    val deviceId: String,
    /** Latitude in decimal degrees. */
    val latitude: Double,
    /** Longitude in decimal degrees. */
    val longitude: Double,
    /** Optional accuracy radius in meters, if available. */
    val accuracyMeters: Float? = null,
    /** Timestamp of the sample in UNIX epoch milliseconds (UTC). */
    val timestampMillis: Long,

)
