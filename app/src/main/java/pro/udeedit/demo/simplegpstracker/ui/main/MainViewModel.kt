package pro.udeedit.demo.simplegpstracker.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    // later: inject use cases / repositories here
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    fun onTrackingToggleChanged(enabled: Boolean) {
        _uiState.value = _uiState.value.copy(isTrackingEnabled = enabled)
        // later: call Start/Stop use cases
    }

    fun onIntervalChanged(minutes: Int) {
        _uiState.value = _uiState.value.copy(intervalMinutes = minutes)
    }

    fun onServerUrlChanged(url: String) {
        _uiState.value = _uiState.value.copy(serverUrl = url)
    }

    fun onSaveClicked() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isSaving = true)
            // later: persist config via use case / DataStore
            _uiState.value = _uiState.value.copy(
                isSaving = false,
                lastStatusMessage = "Settings saved"
            )
        }
    }

    fun onStatusMessageShown() {
        _uiState.value = _uiState.value.copy(lastStatusMessage = null)
    }
}
