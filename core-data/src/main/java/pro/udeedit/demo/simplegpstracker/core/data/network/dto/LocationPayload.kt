package pro.udeedit.demo.simplegpstracker.core.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class LocationPayload(
    val deviceId: String,
    val latitude: Double,
    val longitude: Double,
    val accuracyMeters: Float? = null,
    val timestampMillis: Long
)