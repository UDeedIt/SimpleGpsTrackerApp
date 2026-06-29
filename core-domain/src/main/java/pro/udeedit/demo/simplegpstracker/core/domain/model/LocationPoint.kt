package pro.udeedit.demo.simplegpstracker.core.domain.model

data class LocationPoint(
    val latitude: Double,
    val longitude: Double,
    val accuracyMeters: Float?,
    val timestampMillis: Long
)

