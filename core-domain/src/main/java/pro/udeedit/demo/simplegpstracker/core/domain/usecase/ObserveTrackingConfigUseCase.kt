package pro.udeedit.demo.simplegpstracker.core.domain.usecase

import kotlinx.coroutines.flow.Flow
import pro.udeedit.demo.simplegpstracker.core.domain.model.TrackingConfig
import pro.udeedit.demo.simplegpstracker.core.domain.repository.TrackingConfigRepository

class ObserveTrackingConfigUseCase(
    private val repository: TrackingConfigRepository
) {
    operator fun invoke(): Flow<TrackingConfig> = repository.observeTrackingConfig()
}
