package pro.udeedit.demo.simplegpstracker.core.data.mapper

import pro.udeedit.demo.simplegpstracker.core.data.network.dto.LocationPayload
import pro.udeedit.demo.simplegpstracker.core.domain.model.LocationPoint
import pro.udeedit.demo.simplegpstracker.core.domain.model.TrackingConfig

/**
 * Mapping utilities between domain location models and network payloads.
 */
object LocationMappers

/**
 * Builds a [LocationPayload] from a domain [LocationPoint] and [TrackingConfig].
 *
 * Uses [TrackingConfig.userId] as the device identifier so the server can
 * associate locations with a particular user/device.
 */
fun LocationPoint.toPayload(config: TrackingConfig): LocationPayload =
    LocationPayload(
        deviceId = config.userId,
        latitude = latitude,
        longitude = longitude,
        accuracyMeters = accuracyMeters,
        timestampMillis = timestampMillis
    )
