package pro.udeedit.demo.simplegpstracker.core.domain.location

import kotlinx.coroutines.flow.Flow
import pro.udeedit.demo.simplegpstracker.core.domain.model.LocationPoint

/**
 * Abstraction for a source of location updates.
 *
 * Implementations are responsible for:
 * - Interacting with platform/location APIs.
 * - Respecting permissions and battery constraints.
 */
interface LocationProvider {

    /**
     * Returns a cold [Flow] of location samples.
     *
     * The caller is responsible for handling permissions and starting/stopping
     * collection as appropriate.
     */
    fun observeLocationUpdates(): Flow<LocationPoint>
}
