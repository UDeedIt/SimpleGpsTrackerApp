package pro.udeedit.demo.simplegpstracker.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pro.udeedit.demo.simplegpstracker.core.data.network.KtorLocationApi
import pro.udeedit.demo.simplegpstracker.core.data.network.LocationApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * Provides a [LocationApi] implementation backed by the Ktor HTTP client.
     *
     * Used by the foreground tracking service to send location payloads
     * to the SimpleGpsTrackerServer backend.
     */
    @Provides
    @Singleton
    fun provideLocationApi(): LocationApi = KtorLocationApi()
}
