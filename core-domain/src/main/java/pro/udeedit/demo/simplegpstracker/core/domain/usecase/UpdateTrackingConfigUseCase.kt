package pro.udeedit.demo.simplegpstracker.core.domain.usecase

import pro.udeedit.demo.simplegpstracker.core.domain.model.TrackingConfig
import pro.udeedit.demo.simplegpstracker.core.domain.repository.TrackingConfigRepository

/**
 * Use case for persisting a new tracking configuration.
 */
class UpdateTrackingConfigUseCase(
    private val repository: TrackingConfigRepository
) {
    /**
     * Updates the stored [TrackingConfig].
     */
    suspend operator fun invoke(config: TrackingConfig) {
        repository.updateTrackingConfig(config)
    }
}
