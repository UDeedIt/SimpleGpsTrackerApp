package pro.udeedit.demo.simplegpstracker.core.domain.usecase

import kotlinx.coroutines.flow.Flow
import pro.udeedit.demo.simplegpstracker.core.domain.model.TrackingConfig
import pro.udeedit.demo.simplegpstracker.core.domain.repository.TrackingConfigRepository

/**
 * Use case for observing the current tracking configuration as a stream.
 */
class ObserveTrackingConfigUseCase(
    private val repository: TrackingConfigRepository
) {
    /**
     * Returns a [Flow] that emits the current [TrackingConfig] and any subsequent updates.
     */
    operator fun invoke(): Flow<TrackingConfig> = repository.observeTrackingConfig()
}
