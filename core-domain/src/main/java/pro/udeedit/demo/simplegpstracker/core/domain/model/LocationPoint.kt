package pro.udeedit.demo.simplegpstracker.core.domain.model

/**
 * Represents a single geographic location sample recorded by the app.
 */
data class LocationPoint(
    val latitude: Double,
    val longitude: Double,
    val accuracyMeters: Float?,
    val timestampMillis: Long
)