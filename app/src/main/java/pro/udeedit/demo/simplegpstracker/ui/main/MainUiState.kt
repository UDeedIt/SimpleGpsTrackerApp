package pro.udeedit.demo.simplegpstracker.ui.main

/**
 * Immutable snapshot of all UI state required by the main screen.
 */
data class MainUiState(
    val isTrackingEnabled: Boolean = false,
    val intervalMinutes: Int = 5,
    val serverUrl: String = "",
    val isSaving: Boolean = false,
    val lastStatusMessage: String? = null,

    val lastLatitude: Double? = null,
    val lastLongitude: Double? = null,
    /** Time of the last location sample (epoch-ms, local time). */
    val lastTimestampMillis: Long? = null,

    val userName: String = ""
)
