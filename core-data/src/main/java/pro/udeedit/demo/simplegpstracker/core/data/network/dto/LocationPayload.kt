package pro.udeedit.demo.simplegpstracker.core.data.network.dto

@Serializable
data class LocationPayload(
    val deviceId: String,
    val latitude: Double,
    val longitude: Double,
    val accuracyMeters: Float? = null,
    val timestampMillis: Long
)