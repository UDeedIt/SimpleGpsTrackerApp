package pro.udeedit.demo.simplegpstracker.core.domain.model

data class TrackingConfig(
    val isTrackingEnabled: Boolean,
    val intervalMinutes: Int,
    val serverUrl: String,
    val apiToken: String?
)
