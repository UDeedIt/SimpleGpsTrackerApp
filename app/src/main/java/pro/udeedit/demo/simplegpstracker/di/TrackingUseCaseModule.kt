package pro.udeedit.demo.simplegpstracker.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pro.udeedit.demo.simplegpstracker.core.domain.repository.TrackingConfigRepository
import pro.udeedit.demo.simplegpstracker.core.domain.usecase.GetTrackingConfigUseCase
import pro.udeedit.demo.simplegpstracker.core.domain.usecase.ObserveTrackingConfigUseCase
import pro.udeedit.demo.simplegpstracker.core.domain.usecase.UpdateTrackingConfigUseCase
import javax.inject.Singleton

/**
 * Provides use cases related to tracking configuration.
 */
@Module
@InstallIn(SingletonComponent::class)
object TrackingUseCaseModule {

    @Provides
    @Singleton
    fun provideObserveTrackingConfigUseCase(
        repository: TrackingConfigRepository
    ): ObserveTrackingConfigUseCase =
        ObserveTrackingConfigUseCase(repository)

    @Provides
    @Singleton
    fun provideGetTrackingConfigUseCase(
        repository: TrackingConfigRepository
    ): GetTrackingConfigUseCase =
        GetTrackingConfigUseCase(repository)

    @Provides
    @Singleton
    fun provideUpdateTrackingConfigUseCase(
        repository: TrackingConfigRepository
    ): UpdateTrackingConfigUseCase =
        UpdateTrackingConfigUseCase(repository)
}
