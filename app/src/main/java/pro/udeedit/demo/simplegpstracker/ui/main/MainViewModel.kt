package pro.udeedit.demo.simplegpstracker.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import pro.udeedit.demo.simplegpstracker.R
import pro.udeedit.demo.simplegpstracker.core.domain.model.TrackingConfig
import pro.udeedit.demo.simplegpstracker.core.domain.usecase.GetTrackingConfigUseCase
import pro.udeedit.demo.simplegpstracker.core.domain.usecase.ObserveLocationUseCase
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
    private val updateTrackingConfig: UpdateTrackingConfigUseCase,
    private val observeLocation: ObserveLocationUseCase,
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

    private var locationJob: Job? = null

    private var lastDomainConfig: TrackingConfig? = null


    init {
        viewModelScope.launch {
            observeTrackingConfig().collectLatest { config ->
                lastDomainConfig = config
                _uiState.value = config.toUiState()
            }
        }
    }

    /**
     * Handles user toggling tracking on or off via the UI.
     *
     * Currently, updates only the local UI state.
     */
    fun onTrackingToggleChanged(enabled: Boolean) {
        _uiState.value = _uiState.value.copy(isTrackingEnabled = enabled)
        updateLocationObservation(enabled)
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

            val existingUserId = lastDomainConfig?.userId
            val config = _uiState.value.toTrackingConfig(existingUserId)
            updateTrackingConfig(config)

            _uiState.value = _uiState.value.copy(
                isSaving = false,
                lastStatusMessage = stringProvider.get(R.string.main_settings_saved)
            )
        }
    }

    /** Handles user editing the user name field. */
    fun onUserNameChanged(name: String) {
        _uiState.value = _uiState.value.copy(userName = name)
    }


    /**
     * Starts or stops collection of location updates based on [shouldTrack].
     *
     * When tracking is enabled, a coroutine in [viewModelScope] collects the
     * location [Flow] and updates [MainUiState.lastLatitude] and
     * [MainUiState.lastLongitude]. When disabled, the active collection job
     * (if any) is canceled.
     */
    private fun updateLocationObservation(shouldTrack: Boolean) {
        if (shouldTrack) {
            if (locationJob?.isActive == true) return

            locationJob = viewModelScope.launch {
                observeLocation().collectLatest { point ->
                    _uiState.value = _uiState.value.copy(
                        lastLatitude = point.latitude,
                        lastLongitude = point.longitude
                    )
                }
            }

        } else {
            locationJob?.cancel()
            locationJob = null
        }
    }
}
