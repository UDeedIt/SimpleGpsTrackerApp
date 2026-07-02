package pro.udeedit.demo.simplegpstracker.ui.main

import pro.udeedit.demo.simplegpstracker.core.domain.model.TrackingConfig

/**
 * Maps domain-level tracking configuration to UI state.
 */
fun TrackingConfig.toUiState(): MainUiState =
    MainUiState(
        isTrackingEnabled = isTrackingEnabled,
        intervalMinutes = intervalMinutes,
        serverUrl = serverUrl,
        userName = userName ?: ""
    )

/**
 * Maps UI state back to a domain-level tracking configuration.
 *
 * @param existingUserId The previously persisted userId, if available.
 * This ensures that we keep a stable identifier for the user/device.
 */
fun MainUiState.toTrackingConfig(existingUserId: String?): TrackingConfig =
    TrackingConfig(
        isTrackingEnabled = isTrackingEnabled,
        intervalMinutes = intervalMinutes,
        serverUrl = serverUrl,
        apiToken = null,                               // no API token in UI yet
        userId = existingUserId.orEmpty(),            // will be generated in data layer if empty
        userName = userName.ifBlank { null }
    )
