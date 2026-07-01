package pro.udeedit.demo.simplegpstracker

import android.Manifest
import android.os.Bundle
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
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
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

                // scope for launching coroutines from Composables
                val coroutineScope = rememberCoroutineScope()

                // handle "granted" and show Snackbar when denied
                val locationPermissionLauncher =
                    rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.RequestPermission()
                    ) { granted ->
                        if (!granted) {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(
                                    message = getString(R.string.main_location_permission_required)
                                )
                            }
                        }
                    }

                // Top-level scaffold hosting the main screen and a SnackbarHost
                // for user-visible messages.
                Scaffold(
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                    modifier = Modifier
                ) { padding ->

                    MainScreen(
                        padding = padding,
                        requestLocationPermission = {
                            locationPermissionLauncher.launch(
                                Manifest.permission.ACCESS_FINE_LOCATION
                            )
                        },
                        snackbarHostState = snackbarHostState
                    )
                }
            }
        }
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
fun MainScreenPreview() {
    SimpleGpsTrackerTheme {
        MainScreen(
            padding = PaddingValues(),
            requestLocationPermission = {},
            snackbarHostState = SnackbarHostState()
        )
    }
}
