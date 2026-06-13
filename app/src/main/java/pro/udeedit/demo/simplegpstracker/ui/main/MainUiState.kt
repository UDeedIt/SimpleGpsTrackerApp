package pro.udeedit.demo.simplegpstracker.ui.main

data class MainUiState(
    val isTrackingEnabled: Boolean = false,
    val intervalMinutes: Int = 5,
    val serverUrl: String = "",
    val isSaving: Boolean = false,
    val lastStatusMessage: String? = null
)
