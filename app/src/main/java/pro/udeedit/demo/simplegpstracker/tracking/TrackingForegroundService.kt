package pro.udeedit.demo.simplegpstracker.tracking

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import pro.udeedit.demo.simplegpstracker.R
import pro.udeedit.demo.simplegpstracker.core.data.mapper.toPayload
import pro.udeedit.demo.simplegpstracker.core.data.network.LocationApi
import pro.udeedit.demo.simplegpstracker.core.data.network.dto.LocationPayload
import pro.udeedit.demo.simplegpstracker.core.domain.model.LocationPoint
import pro.udeedit.demo.simplegpstracker.core.domain.model.TrackingConfig
import pro.udeedit.demo.simplegpstracker.core.domain.repository.TrackingConfigRepository
import pro.udeedit.demo.simplegpstracker.core.domain.usecase.ObserveLocationUseCase
import javax.inject.Inject

/**
 * Foreground service responsible for continuous location tracking.
 *
 * This service:
 * - Runs as a foreground service with a persistent notification.
 * - Collects location updates from [ObserveLocationUseCase].
 *
 * It does not yet send locations to a server; that will be added later.
 */
@AndroidEntryPoint
class TrackingForegroundService : Service() {

    @Inject
    lateinit var observeLocationUseCase: ObserveLocationUseCase

    /**
     * Repository for reading the current tracking configuration, including
     * the server URL, user ID and optional API token.
     */
    @Inject
    lateinit var trackingConfigRepository: TrackingConfigRepository

    /**
     * HTTP client abstraction used to send location payloads to the backend.
     */
    @Inject
    lateinit var locationApi: LocationApi


    private val serviceJob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.IO + serviceJob)

    private var locationCollectionJob: Job? = null

    override fun onBind(intent: Intent?): IBinder? = null

    /**
     * Called when the service is started via [Context.startForegroundService] or [Context.startService].
     *
     * Starts the service in the foreground and begins collecting location updates.
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannelIfNeeded()
        val notification = buildNotification()
        startForeground(NOTIFICATION_ID, notification)

        startCollectingLocations()

        // START_STICKY indicates the system should try to recreate the service if killed.
        return START_STICKY
    }

    /**
     * Cleans up running coroutines when the service is destroyed.
     */
    override fun onDestroy() {
        super.onDestroy()
        locationCollectionJob?.cancel()
        serviceScope.cancel()
    }

    /**
     * Starts collecting location updates and sending them to the backend.
     *
     * Behavior:
     * - Reads the current [TrackingConfig] from [trackingConfigRepository] to obtain
     *   the user-entered base `serverUrl`, `userId` and optional `apiToken`.
     * - Builds the full endpoint URL by appending the fixed path `/api/v1/locations`
     *   to the base URL (trimming any trailing slash).
     * - Collects [LocationPoint] values from [observeLocationUseCase].
     * - Converts each [LocationPoint] to a [LocationPayload] using `toPayload`.
     * - Uses [locationApi] to POST the payload to the configured server endpoint
     *   and logs the HTTP response status code.
     *
     * If the base `serverUrl` is blank, the function returns without starting
     * collection, to avoid unnecessary work.
     */
    private fun startCollectingLocations() {
        if (locationCollectionJob?.isActive == true) return

        locationCollectionJob = serviceScope.launch {
            // Read current config (user-entered base URL, userId, apiToken, etc.)
            val config = trackingConfigRepository.getTrackingConfig()

            // Base URL must be set by the user in settings
            if (config.serverUrl.isBlank()) {
                Log.w("TrackingService", "Server URL is blank; not starting location sending.")
                return@launch
            }

            // Build full endpoint URL by appending the fixed API path
            val fullUrl = config.serverUrl.trimEnd('/') + "/api/v1/locations"

            Log.i("TrackingService", "Starting location collection; sending to $fullUrl")

            observeLocationUseCase().collectLatest { locationPoint ->
                val payload = locationPoint.toPayload(config)
                try {
                    val result = locationApi.sendLocation(
                        serverUrl = fullUrl,
                        apiToken = config.apiToken,
                        payload = payload
                    )

                    val code = result.statusCode
                    val body = result.body

                    if (code in 200..299) {
                        Log.d("TrackingService", "Sent location successfully (HTTP $code, body=$body): $payload")

                    } else {
                        Log.w("TrackingService", "Server responded with HTTP $code, body=$body for location: $payload")
                    }

                } catch (t: Throwable) {
                    Log.e("TrackingService", "Failed to send location: $payload", t)
                }
            }
        }
    }


    /**
     * Builds the notification shown while the service is running in the foreground.
     */
    private fun buildNotification(): Notification {
        val channelId = getString(R.string.tracking_notification_channel_id)

        return NotificationCompat.Builder(this, channelId)
            .setContentTitle(getString(R.string.tracking_notification_title))
            .setContentText(getString(R.string.tracking_notification_text))
            .setSmallIcon(R.drawable.ic_launcher_foreground) // replace with a better icon later
            .setOngoing(true)
            .build()
    }

    /**
     * Creates the notification channel required for foreground notifications
     * on Android 8.0+ (API level 26+).
     */
    private fun createNotificationChannelIfNeeded() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

        val channelId = getString(R.string.tracking_notification_channel_id)
        val channelName = getString(R.string.tracking_notification_channel_name)
        val channelDescription = getString(R.string.tracking_notification_channel_description)

        val channel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            description = channelDescription
        }

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }

    companion object {
        private const val NOTIFICATION_ID = 1001
    }
}
