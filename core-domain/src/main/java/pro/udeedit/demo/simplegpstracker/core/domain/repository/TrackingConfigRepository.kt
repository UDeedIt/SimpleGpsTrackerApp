package pro.udeedit.demo.simplegpstracker.core.domain.repository

import kotlinx.coroutines.flow.Flow
import pro.udeedit.demo.simplegpstracker.core.domain.model.TrackingConfig

/**
 * Abstraction for reading and writing tracking configuration.
 *
 * Implementations may persist data in DataStore, a database, or any other storage.
 * This interface lives in the domain layer and should be platform‑agnostic.
 */
interface TrackingConfigRepository {

    /**
     * Stream of the current tracking configuration.
     *
     * Implementations should emit:
     * - the current value on subscription,
     * - and any subsequent updates when the configuration changes.
     */
    fun observeTrackingConfig(): Flow<TrackingConfig>

    /**
     * Returns the latest tracking configuration once.
     *
     * May perform I/O (e.g. read from disk), so it is a suspending call.
     */
    suspend fun getTrackingConfig(): TrackingConfig

    /**
     * Persists a new tracking configuration.
     *
     * Implementations should ensure that [observeTrackingConfig] emits the updated value.
     */
    suspend fun updateTrackingConfig(config: TrackingConfig)
}
