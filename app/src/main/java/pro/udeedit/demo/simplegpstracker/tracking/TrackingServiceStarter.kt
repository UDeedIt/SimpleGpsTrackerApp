package pro.udeedit.demo.simplegpstracker.tracking

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat

/**
 * Starts [TrackingForegroundService] as a foreground service.
 */
fun Context.startTrackingService() {
    val intent = Intent(this, TrackingForegroundService::class.java)
    ContextCompat.startForegroundService(this, intent)
}

/**
 * Stops [TrackingForegroundService] if it is running.
 */
fun Context.stopTrackingService() {
    val intent = Intent(this, TrackingForegroundService::class.java)
    stopService(intent)
}
