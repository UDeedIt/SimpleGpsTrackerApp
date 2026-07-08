package pro.udeedit.demo.simplegpstracker.core.domain.model

/**
 * User‑configurable options that control how tracking behaves.
 *
 * @param isTrackingEnabled Whether tracking is currently enabled according to the user's settings.
 * @param intervalMinutes Interval between location samples, in minutes.
 * @param serverUrl Base URL of the server that receives location updates.
 * @param apiToken Optional API token used for authenticating requests to [serverUrl].
 * @param userId Stable identifier for the user/device, generated once and reused.
 * @param userName Optional user-friendly name entered in the app.
 */
data class TrackingConfig(
    val isTrackingEnabled: Boolean,
    val intervalMinutes: Int,
    val serverUrl: String,
    val apiToken: String?,
    val userId: String,
    val userName: String?
)
