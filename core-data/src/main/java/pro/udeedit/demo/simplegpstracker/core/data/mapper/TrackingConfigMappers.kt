package pro.udeedit.demo.simplegpstracker.core.data.mapper

import pro.udeedit.demo.simplegpstracker.core.data.datastore.TrackingConfigDto
import pro.udeedit.demo.simplegpstracker.core.domain.model.TrackingConfig

/**
 * Maps [TrackingConfigDto] (storage) to [TrackingConfig] (domain).
 */
fun TrackingConfigDto.toDomain(): TrackingConfig =
    TrackingConfig(
        isTrackingEnabled = isTrackingEnabled,
        intervalMinutes = intervalMinutes,
        serverUrl = serverUrl,
        apiToken = apiToken
    )

/**
 * Maps [TrackingConfig] (domain) to [TrackingConfigDto] (storage).
 */
fun TrackingConfig.toDto(): TrackingConfigDto =
    TrackingConfigDto(
        isTrackingEnabled = isTrackingEnabled,
        intervalMinutes = intervalMinutes,
        serverUrl = serverUrl,
        apiToken = apiToken
    )
