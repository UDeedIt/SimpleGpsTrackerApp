package pro.udeedit.demo.simplegpstracker.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pro.udeedit.demo.simplegpstracker.core.domain.location.LocationProvider
import pro.udeedit.demo.simplegpstracker.core.domain.usecase.ObserveLocationUseCase
import pro.udeedit.demo.simplegpstracker.tracking.FusedLocationProvider
import javax.inject.Singleton

/**
 * Provides location-related dependencies:
 * - [LocationProvider] implementation
 * - [ObserveLocationUseCase]
 */
@Module
@InstallIn(SingletonComponent::class)
object LocationModule {

    @Provides
    @Singleton
    fun provideLocationProvider(
        @ApplicationContext context: Context
    ): LocationProvider = FusedLocationProvider(context)

    @Provides
    @Singleton
    fun provideObserveLocationUseCase(
        locationProvider: LocationProvider
    ): ObserveLocationUseCase = ObserveLocationUseCase(locationProvider)
}
