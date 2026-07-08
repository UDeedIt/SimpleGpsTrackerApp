package pro.udeedit.demo.simplegpstracker.core.data.mapper

import java.util.UUID
import pro.udeedit.demo.simplegpstracker.core.data.datastore.TrackingConfigDto
import pro.udeedit.demo.simplegpstracker.core.domain.model.TrackingConfig

private const val DEFAULT_USER_NAME = ""

/**
 * Maps [TrackingConfigDto] (storage) to [TrackingConfig] (domain).
 * If `userId` is missing, generates a new one.
 */
fun TrackingConfigDto.toDomain(): TrackingConfig =
    TrackingConfig(
        isTrackingEnabled = isTrackingEnabled,
        intervalMinutes = intervalMinutes,
        serverUrl = serverUrl,
        apiToken = apiToken,
        userId = userId ?: UUID.randomUUID().toString(),
        userName = userName ?: DEFAULT_USER_NAME
    )

/**
 * Maps [TrackingConfig] (domain) to [TrackingConfigDto] (storage).
 */
fun TrackingConfig.toDto(): TrackingConfigDto =
    TrackingConfigDto(
        isTrackingEnabled = isTrackingEnabled,
        intervalMinutes = intervalMinutes,
        serverUrl = serverUrl,
        apiToken = apiToken,
        userId = userId,
        userName = userName
    )