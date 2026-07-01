package pro.udeedit.demo.simplegpstracker.core.domain.model

/**
 * User‑configurable options that control how tracking behaves.
 */
data class TrackingConfig(
    val isTrackingEnabled: Boolean,
    val intervalMinutes: Int,
    val serverUrl: String,
    val apiToken: String?
)
