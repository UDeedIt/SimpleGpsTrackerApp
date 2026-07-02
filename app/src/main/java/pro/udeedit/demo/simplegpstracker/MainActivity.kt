package pro.udeedit.demo.simplegpstracker

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import kotlinx.coroutines.launch
import pro.udeedit.demo.simplegpstracker.tracking.startTrackingService
import pro.udeedit.demo.simplegpstracker.tracking.stopTrackingService
import pro.udeedit.demo.simplegpstracker.ui.main.MainScreen
import pro.udeedit.demo.simplegpstracker.ui.theme.SimpleGpsTrackerTheme

/**
 * Main entry Activity for SimpleGpsTracker.
 *
 * Responsibilities:
 * - Configure window (edge-to-edge).
 * - Set the root Compose content and theme.
 * - Provide a [SnackbarHostState] for transient messages.
 * - Expose a launcher for requesting location permission at runtime and
 *   pass it down to the composable layer.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    /**
     * Called when the activity is starting.
     *
     * Sets up the Compose hierarchy and prepares the permission launcher and
     * snackbar host state that the UI will use.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SimpleGpsTrackerTheme {
                // Shared host for Snackbars displayed from the main screen.
                val snackbarHostState = remember { SnackbarHostState() }
                val coroutineScope = rememberCoroutineScope()

                // Holds the callback that MainScreen provides when it requests permission.
                var permissionResultCallback by remember {
                    mutableStateOf<((Boolean) -> Unit)?>(null)
                }

                val locationPermissionLauncher =
                    rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.RequestPermission()

                    ) { granted: Boolean ->

                        // Notify the screen/ViewModel about the result first.
                        permissionResultCallback?.invoke(granted)

                        if (!granted) {
                            // Check whether we should show rationale. If not, this usually means
                            // the user selected "Don't ask again" -> permanent denial.
                            val shouldShowRationale =
                                ActivityCompat.shouldShowRequestPermissionRationale(
                                    this,
                                    Manifest.permission.ACCESS_FINE_LOCATION
                                )

                            coroutineScope.launch {
                                if (!shouldShowRationale) {
                                    // Permanent denial: inform the user and open app settings.
                                    snackbarHostState.showSnackbar(
                                        message = getString(
                                            R.string.main_location_permission_permanently_denied
                                        )
                                    )

                                    openAppSettings()

                                } else {
                                    // Simple denial: inform that permission is required.
                                    snackbarHostState.showSnackbar(
                                        message = getString(R.string.main_location_permission_required)
                                    )
                                }
                            }
                        }
                    }


                Scaffold(
                    snackbarHost = {
                        SnackbarHost(hostState = snackbarHostState)
                    },
                    modifier = Modifier

                ) { padding ->

                    MainScreen(
                        padding = padding,
                        requestLocationPermission = { onResult ->
                            permissionResultCallback = onResult
                            locationPermissionLauncher.launch(
                                Manifest.permission.ACCESS_FINE_LOCATION
                            )
                        },
                        snackbarHostState = snackbarHostState,
                        onStartTrackingRequested = { startTrackingService() },
                        onStopTrackingRequested = { stopTrackingService() }
                    )
                }
            }
        }
    }

    /**
     * Opens the application details settings screen so the user can manually
     * grant the location permission after a permanent denial.
     */
    private fun openAppSettings() {
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", packageName, null)
        )
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

}

// PREVIEW

/**
 * Design-time preview of [MainScreen].
 *
 * Uses dummy padding and a no-op permission callback / snackbar host state.
 */
@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    // Simple preview using dummy values for dependencies.
    pro.udeedit.demo.simplegpstracker.ui.theme.SimpleGpsTrackerTheme {
        MainScreen(
            padding = PaddingValues(),
            requestLocationPermission = { onResult ->
                // Simulate permission granted in preview.
                onResult(true)
            },
            snackbarHostState = SnackbarHostState(),
            onStartTrackingRequested = {},
            onStopTrackingRequested = {}
        )
    }
}

