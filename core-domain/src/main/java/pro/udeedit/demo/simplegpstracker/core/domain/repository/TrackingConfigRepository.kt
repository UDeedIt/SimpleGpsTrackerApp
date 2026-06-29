package pro.udeedit.demo.simplegpstracker.core.domain.repository

import kotlinx.coroutines.flow.Flow
import pro.udeedit.demo.simplegpstracker.core.domain.model.TrackingConfig

interface TrackingConfigRepository {

    /** Stream of current tracking configuration (updates when changed). */
    fun observeTrackingConfig(): Flow<TrackingConfig>

    /** Get the latest config once. */
    suspend fun getTrackingConfig(): TrackingConfig

    /** Persist a new configuration. */
    suspend fun updateTrackingConfig(config: TrackingConfig)
}
