package pro.udeedit.demo.simplegpstracker.core.domain.usecase

import pro.udeedit.demo.simplegpstracker.core.domain.model.TrackingConfig
import pro.udeedit.demo.simplegpstracker.core.domain.repository.TrackingConfigRepository

/**
 * Use case for retrieving the current tracking configuration once.
 */
class GetTrackingConfigUseCase(
    private val repository: TrackingConfigRepository
) {
    /**
     * Returns the latest [TrackingConfig].
     */
    suspend operator fun invoke(): TrackingConfig = repository.getTrackingConfig()
}
