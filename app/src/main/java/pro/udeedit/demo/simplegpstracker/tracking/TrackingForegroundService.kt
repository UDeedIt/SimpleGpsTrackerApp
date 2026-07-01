package pro.udeedit.demo.simplegpstracker.tracking

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import pro.udeedit.demo.simplegpstracker.R
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
     * Starts collecting location updates from [ObserveLocationUseCase].
     *
     * Currently this just collects the stream; later we will add logic
     * to buffer and send locations to the configured server.
     */
    private fun startCollectingLocations() {
        if (locationCollectionJob?.isActive == true) return

        locationCollectionJob = serviceScope.launch {
            observeLocationUseCase().collectLatest { locationPoint ->
                // TODO: send locationPoint to repository / server
                // For now, this is a placeholder to show where tracking occurs.
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
