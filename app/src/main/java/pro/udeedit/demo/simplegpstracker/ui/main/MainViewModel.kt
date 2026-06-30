package pro.udeedit.demo.simplegpstracker.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import pro.udeedit.demo.simplegpstracker.R
import pro.udeedit.demo.simplegpstracker.core.domain.model.TrackingConfig
import pro.udeedit.demo.simplegpstracker.core.domain.usecase.GetTrackingConfigUseCase
import pro.udeedit.demo.simplegpstracker.core.domain.usecase.ObserveTrackingConfigUseCase
import pro.udeedit.demo.simplegpstracker.core.domain.usecase.UpdateTrackingConfigUseCase
import pro.udeedit.demo.simplegpstracker.util.StringProvider
import javax.inject.Inject

/**
 * ViewModel for the main screen, responsible for:
 *
 * - Exposing the current tracking configuration as [MainUiState]
 * - Handling user interactions (toggling tracking, changing interval/URL, saving)
 * - Coordinating with domain use cases to load and persist [TrackingConfig]
 *
 * This ViewModel uses [viewModelScope] and Kotlin coroutines for async work,
 * ensuring that any ongoing jobs are canceled automatically when the ViewModel
 * is cleared.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val observeTrackingConfig: ObserveTrackingConfigUseCase,
    private val getTrackingConfig: GetTrackingConfigUseCase,
    private val updateTrackingConfig: UpdateTrackingConfigUseCase,
    private val stringProvider: StringProvider,

) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())

    /**
     * Immutable stream of UI state that the Composables observe.
     *
     * The UI should *not* mutate this state directly; instead it should call
     * the intent functions (e.g. [onTrackingToggleChanged]).
     */
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    init {
        // Keep the UI in sync with the stored configuration.
        viewModelScope.launch {
            observeTrackingConfig().collectLatest { config ->
                _uiState.value = config.toUiState()
            }
        }
    }

    /**
     * Handles user toggling tracking on or off via the UI.
     *
     * Currently updates only the local UI state.
     */
    fun onTrackingToggleChanged(enabled: Boolean) {
        _uiState.value = _uiState.value.copy(isTrackingEnabled = enabled)
    }

    /**
     * Handles user changing the tracking interval in minutes.
     */
    fun onIntervalChanged(minutes: Int) {
        _uiState.value = _uiState.value.copy(intervalMinutes = minutes)
    }

    /**
     * Handles user editing the server URL.
     */
    fun onServerUrlChanged(url: String) {
        _uiState.value = _uiState.value.copy(serverUrl = url)
    }

    /**
     * Called when the user presses the "Save" button.
     *
     * This launches a coroutine in [viewModelScope] to:
     * - mark the UI as "saving",
     * - persist the configuration using [UpdateTrackingConfigUseCase],
     * - and then update the status message.
     */
    fun onSaveClicked() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isSaving = true)

            val config = _uiState.value.toTrackingConfig()
            updateTrackingConfig(config)

            _uiState.value = _uiState.value.copy(
                isSaving = false,
                lastStatusMessage = stringProvider.get(R.string.main_settings_saved)
            )
        }
    }


    /**
     * Clears the transient status message after it has been shown by the UI
     * (e.g. in a Snackbar).
     */
    fun onStatusMessageShown() {
        _uiState.value = _uiState.value.copy(lastStatusMessage = null)
    }
}
