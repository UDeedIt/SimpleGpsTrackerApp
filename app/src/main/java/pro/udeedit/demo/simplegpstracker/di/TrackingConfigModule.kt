package pro.udeedit.demo.simplegpstracker.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pro.udeedit.demo.simplegpstracker.core.data.datastore.TrackingConfigDataStore
import pro.udeedit.demo.simplegpstracker.core.data.datastore.trackingConfigDataStore
import pro.udeedit.demo.simplegpstracker.core.data.repository.TrackingConfigRepositoryImpl
import pro.udeedit.demo.simplegpstracker.core.domain.repository.TrackingConfigRepository
import javax.inject.Singleton

/**
 * Provides DataStore and repository implementations related to tracking configuration.
 */
@Module
@InstallIn(SingletonComponent::class)
object TrackingConfigModule {

    @Provides
    @Singleton
    fun provideTrackingConfigDataStore(
        @ApplicationContext context: Context
    ): TrackingConfigDataStore =
        TrackingConfigDataStore(context.trackingConfigDataStore)

    @Provides
    @Singleton
    fun provideTrackingConfigRepository(
        dataStore: TrackingConfigDataStore
    ): TrackingConfigRepository =
        TrackingConfigRepositoryImpl(dataStore)
}
