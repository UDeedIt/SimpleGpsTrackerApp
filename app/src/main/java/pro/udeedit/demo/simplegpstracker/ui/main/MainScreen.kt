package pro.udeedit.demo.simplegpstracker.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import pro.udeedit.demo.simplegpstracker.R

/**
 * Entry composable for the main screen.
 *
 * This function:
 * - Obtains the [MainViewModel] via Hilt.
 * - Collects the [MainUiState] as Compose state.
 * - Delegates actual UI rendering to [MainScreenContent].
 *
 * @param padding Padding from the parent [Scaffold] in [MainActivity].
 * @param requestLocationPermission Callback that triggers a runtime location
 * permission request from the Activity.
 * @param snackbarHostState [SnackbarHostState] for showing transient messages.
 * @param viewModel Hilt-injected [MainViewModel] that owns the screen state.
 */
@Composable
fun MainScreen(
    padding: PaddingValues,
    requestLocationPermission: () -> Unit,
    snackbarHostState: SnackbarHostState,
    viewModel: MainViewModel = hiltViewModel()
) {
    // Collect the latest UI state from the ViewModel, respecting lifecycle.
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MainScreenContent(
        state = uiState,
        padding = padding,
        onTrackingToggleChanged = { enabled ->
            // When user enables tracking, ask for location permission first.
            if (enabled) {
                requestLocationPermission()
            }
            viewModel.onTrackingToggleChanged(enabled)
        },
        onIntervalChanged = viewModel::onIntervalChanged,
        onServerUrlChanged = viewModel::onServerUrlChanged,
        onSaveClicked = viewModel::onSaveClicked
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainScreenContent(
    state: MainUiState,
    padding: PaddingValues,
    onTrackingToggleChanged: (Boolean) -> Unit,
    onIntervalChanged: (Int) -> Unit,
    onServerUrlChanged: (String) -> Unit,
    onSaveClicked: () -> Unit
) {
    /**
     * Top-level scaffold for the main screen.
     *
     * - Hosts a top app bar with the title.
     * - Provides [innerPadding] to content, which we combine with external [padding]
     *   from the parent scaffold (in [MainActivity]) and our own fixed content padding.
     */
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.main_title)) }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                // Padding from this Scaffold (e.g. for the top app bar)
                .padding(innerPadding)
                // Additional padding passed down from the parent Scaffold
                .padding(padding)
                // Fixed content padding for this screen
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            /**
             * Tracking toggle row:
             * - Label describing the setting.
             * - [Switch] bound to [state.isTrackingEnabled].
             */
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(stringResource(R.string.main_tracking_label))
                Switch(
                    checked = state.isTrackingEnabled,
                    onCheckedChange = onTrackingToggleChanged
                )
            }

            /**
             * Interval input:
             * - Backed by [state.intervalMinutes].
             * - Parses user input to Int; invalid values are ignored.
             */
            OutlinedTextField(
                value = state.intervalMinutes.toString(),
                onValueChange = { text ->
                    text.toIntOrNull()?.let { onIntervalChanged(it) }
                },
                label = { Text(stringResource(R.string.main_interval_label)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            /**
             * Server URL input:
             * - Backed by [state.serverUrl].
             * - Used to configure the endpoint for sending location data.
             */
            OutlinedTextField(
                value = state.serverUrl,
                onValueChange = onServerUrlChanged,
                label = { Text(stringResource(R.string.main_server_url_label)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            /**
             * Save button:
             * - Disabled while [state.isSaving] is true.
             * - Triggers [onSaveClicked] to persist the configuration.
             * - Shows "Saving..." vs "Save" based on [state.isSaving].
             */
            Button(
                onClick = onSaveClicked,
                enabled = !state.isSaving,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(
                    text = if (state.isSaving) {
                        stringResource(R.string.main_saving)
                    } else {
                        stringResource(R.string.main_save)
                    }
                )
            }

            /**
             * Last known location display:
             * - Shown only when both latitude and longitude are available.
             * - Uses localized label and formatted coordinates.
             */
            if (state.lastLatitude != null && state.lastLongitude != null) {
                Column {
                    Text(text = stringResource(R.string.main_last_location_label))
                    Text(
                        text = stringResource(
                            R.string.main_last_location_format,
                            state.lastLatitude,
                            state.lastLongitude
                        )
                    )
                }
            }
        }
    }
}
