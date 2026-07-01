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
     * Returns a cold [Flow] of [LocationPoint] values.
     *
     * The caller is responsible for starting and stopping collection and for
     * handling runtime permissions.
     */
    fun observeLocationUpdates(): Flow<LocationPoint>
}
