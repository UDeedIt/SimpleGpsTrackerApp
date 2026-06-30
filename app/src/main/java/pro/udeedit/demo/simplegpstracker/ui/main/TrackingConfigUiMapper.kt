package pro.udeedit.demo.simplegpstracker.ui.main

import pro.udeedit.demo.simplegpstracker.core.domain.model.TrackingConfig

/**
 * Maps domain-level tracking configuration to UI state.
 */
fun TrackingConfig.toUiState(): MainUiState =
    MainUiState(
        isTrackingEnabled = isTrackingEnabled,
        intervalMinutes = intervalMinutes,
        serverUrl = serverUrl
    )

/**
 * Maps UI state back to a domain-level tracking configuration.
 */
fun MainUiState.toTrackingConfig(): TrackingConfig =
    TrackingConfig(
        isTrackingEnabled = isTrackingEnabled,
        intervalMinutes = intervalMinutes,
        serverUrl = serverUrl,
        apiToken = null // no API token field in UI yet
    )
