package pro.udeedit.demo.simplegpstracker.core.domain.usecase

import kotlinx.coroutines.flow.Flow
import pro.udeedit.demo.simplegpstracker.core.domain.location.LocationProvider
import pro.udeedit.demo.simplegpstracker.core.domain.model.LocationPoint

/**
 * Use case that exposes a stream of [LocationPoint] from the underlying
 * [LocationProvider].
 */
class ObserveLocationUseCase(
    private val locationProvider: LocationProvider
) {

    operator fun invoke(): Flow<LocationPoint> = locationProvider.observeLocationUpdates()
}
