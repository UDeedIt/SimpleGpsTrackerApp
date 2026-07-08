package pro.udeedit.demo.simplegpstracker.core.data.datastore

import kotlinx.serialization.Serializable

/**
 * Serializable representation of tracking configuration for persistence.
 *
 * This is separate from the domain model to keep storage concerns out of the domain layer.
 */
@Serializable
data class TrackingConfigDto(
    val isTrackingEnabled: Boolean = false,
    val intervalMinutes: Int = 5,
    val serverUrl: String = "",
    val apiToken: String? = null,
    val userId: String? = null,
    val userName: String? = null
)
