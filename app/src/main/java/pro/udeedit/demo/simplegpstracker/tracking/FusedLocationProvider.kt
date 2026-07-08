package pro.udeedit.demo.simplegpstracker.tracking

import android.annotation.SuppressLint
import android.location.Location
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.isActive
import pro.udeedit.demo.simplegpstracker.core.domain.location.LocationProvider
import pro.udeedit.demo.simplegpstracker.core.domain.model.LocationPoint
import javax.inject.Inject
import javax.inject.Singleton

/**
 * [LocationProvider] implementation backed by Fused Location Provider.
 *
 * This class assumes that the required location permissions have already been granted.
 */
@Singleton
class FusedLocationProvider @Inject constructor(
    private val context: android.content.Context
) : LocationProvider {

    private val client by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    /**
     * Emits continuous location updates as [LocationPoint] values.
     */
    @SuppressLint("MissingPermission") // caller is responsible for permission checks
    override fun observeLocationUpdates(): Flow<LocationPoint> = callbackFlow {
        val request = LocationRequest.Builder(10_000L)
            .setMinUpdateIntervalMillis(5_000L)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .build()

        val callback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                if (!isActive) return
                for (location in result.locations) {
                    trySend(location.toLocationPoint())
                }
            }
        }

        client.requestLocationUpdates(request, callback, context.mainLooper)

        awaitClose {
            client.removeLocationUpdates(callback)
        }
    }

    private fun Location.toLocationPoint(): LocationPoint =
        LocationPoint(
            latitude = latitude,
            longitude = longitude,
            accuracyMeters = if (hasAccuracy()) accuracy else null,
            timestampMillis = time
        )
}
