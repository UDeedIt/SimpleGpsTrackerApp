package pro.udeedit.demo.simplegpstracker.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MainScreenContent(
        state = uiState,
        onTrackingToggleChanged = viewModel::onTrackingToggleChanged,
        onIntervalChanged = viewModel::onIntervalChanged,
        onServerUrlChanged = viewModel::onServerUrlChanged,
        onSaveClicked = viewModel::onSaveClicked
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainScreenContent(
    state: MainUiState,
    onTrackingToggleChanged: (Boolean) -> Unit,
    onIntervalChanged: (Int) -> Unit,
    onServerUrlChanged: (String) -> Unit,
    onSaveClicked: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("SimpleGpsTracker") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Tracking")
                Switch(
                    checked = state.isTrackingEnabled,
                    onCheckedChange = onTrackingToggleChanged
                )
            }

            // Interval
            OutlinedTextField(
                value = state.intervalMinutes.toString(),
                onValueChange = { text ->
                    text.toIntOrNull()?.let { onIntervalChanged(it) }
                },
                label = { Text("Interval (minutes)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Server URL
            OutlinedTextField(
                value = state.serverUrl,
                onValueChange = onServerUrlChanged,
                label = { Text("Server URL") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Button(
                onClick = onSaveClicked,
                enabled = !state.isSaving,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(if (state.isSaving) "Saving..." else "Save")
            }
        }
    }
}
