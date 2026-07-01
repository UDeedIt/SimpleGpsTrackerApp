package pro.udeedit.demo.simplegpstracker.core.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val TRACKING_CONFIG_PREFS = "tracking_config_prefs"

// Extension to get the DataStore instance from a Context
val Context.trackingConfigDataStore: DataStore<Preferences> by preferencesDataStore(
    name = TRACKING_CONFIG_PREFS
)

/**
 * Thin wrapper around Preferences DataStore for reading/writing [TrackingConfigDto].
 */
class TrackingConfigDataStore(
    private val dataStore: DataStore<Preferences>
) {

    private object Keys {
        val IS_TRACKING_ENABLED = booleanPreferencesKey("is_tracking_enabled")
        val INTERVAL_MINUTES   = intPreferencesKey("interval_minutes")
        val SERVER_URL         = stringPreferencesKey("server_url")
        val API_TOKEN          = stringPreferencesKey("api_token")
    }

    /**
     * Stream of [TrackingConfigDto]. Emits defaults when no values are stored yet.
     */
    val configFlow: Flow<TrackingConfigDto> = dataStore.data.map { prefs ->
        TrackingConfigDto(
            isTrackingEnabled = prefs[Keys.IS_TRACKING_ENABLED] ?: false,
            intervalMinutes   = prefs[Keys.INTERVAL_MINUTES] ?: 5,
            serverUrl         = prefs[Keys.SERVER_URL] ?: "",
            apiToken          = prefs[Keys.API_TOKEN]
        )
    }

    /**
     * Persists the given [TrackingConfigDto] to DataStore.
     */
    suspend fun setConfig(dto: TrackingConfigDto) {
        dataStore.edit { prefs ->
            prefs[Keys.IS_TRACKING_ENABLED] = dto.isTrackingEnabled
            prefs[Keys.INTERVAL_MINUTES]   = dto.intervalMinutes
            prefs[Keys.SERVER_URL]         = dto.serverUrl
            if (dto.apiToken != null) {
                prefs[Keys.API_TOKEN] = dto.apiToken
            } else {
                prefs.remove(Keys.API_TOKEN)
            }
        }
    }
}
