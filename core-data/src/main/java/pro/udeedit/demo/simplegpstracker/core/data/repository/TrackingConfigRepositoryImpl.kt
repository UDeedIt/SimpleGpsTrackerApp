package pro.udeedit.demo.simplegpstracker.core.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import pro.udeedit.demo.simplegpstracker.core.data.datastore.TrackingConfigDataStore
import pro.udeedit.demo.simplegpstracker.core.data.mapper.toDomain
import pro.udeedit.demo.simplegpstracker.core.data.mapper.toDto
import pro.udeedit.demo.simplegpstracker.core.domain.model.TrackingConfig
import pro.udeedit.demo.simplegpstracker.core.domain.repository.TrackingConfigRepository

/**
 * DataStore‑backed implementation of [TrackingConfigRepository].
 */
class TrackingConfigRepositoryImpl(
    private val dataStore: TrackingConfigDataStore
) : TrackingConfigRepository {

    override fun observeTrackingConfig(): Flow<TrackingConfig> =
        dataStore.configFlow.map { dto -> dto.toDomain() }

    override suspend fun getTrackingConfig(): TrackingConfig =
        dataStore.configFlow.first().toDomain()

    override suspend fun updateTrackingConfig(config: TrackingConfig) {
        dataStore.setConfig(config.toDto())
    }
}
