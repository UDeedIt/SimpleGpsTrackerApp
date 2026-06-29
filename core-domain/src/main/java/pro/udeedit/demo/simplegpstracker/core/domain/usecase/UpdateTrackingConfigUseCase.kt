package pro.udeedit.demo.simplegpstracker.core.domain.usecase

import pro.udeedit.demo.simplegpstracker.core.domain.model.TrackingConfig
import pro.udeedit.demo.simplegpstracker.core.domain.repository.TrackingConfigRepository

class UpdateTrackingConfigUseCase(
    private val repository: TrackingConfigRepository
) {
    suspend operator fun invoke(config: TrackingConfig) {
        repository.updateTrackingConfig(config)
    }
}
