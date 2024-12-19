package com.golyv.forecast.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.golyv.forecast.domain.usecase.FetchCapturedImages
import com.golyv.forecast.domain.usecase.GetWeatherForecastUseCase
import com.golyv.forecast.domain.usecase.SaveImageUri
import com.golyv.uicomponents.screens.error.model.Error
import com.golyv.forecast.presentation.model.ForecastUiEvent
import com.golyv.forecast.presentation.model.ForecastUiState
import com.golyv.network.model.ResultState
import com.golyv.network.util.NetworkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherForecastViewModel @Inject constructor(
    networkManager: NetworkManager,
    private val getWeatherForecastUseCase: GetWeatherForecastUseCase,
    private val saveImageUri: SaveImageUri,
    private val fetchCapturedImages: FetchCapturedImages,
) : ViewModel() {

    val hasInternet: StateFlow<Boolean> = networkManager.hasInternet.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(1_000),
        initialValue = true
    )

    private val _forecastUiState: MutableStateFlow<ForecastUiState> =
        MutableStateFlow(ForecastUiState.Loading)
    val forecastUiState get() = _forecastUiState.asStateFlow()

    private val _capturedImageUri: MutableStateFlow<String?> =
        MutableStateFlow(null)
    val capturedImageUri get() = _capturedImageUri.asStateFlow()

    private val _imagesHistory: MutableStateFlow<List<String>?> =
        MutableStateFlow(null)
    val imagesHistory get() = _imagesHistory.asStateFlow()

    fun onUiEvent(forecastUiEvent: ForecastUiEvent) {
        when (forecastUiEvent) {
            ForecastUiEvent.PermissionDenied -> {
                _forecastUiState.value =
                    ForecastUiState.Failure(Error.PERMISSION_DENIED.value)
            }

            is ForecastUiEvent.PermissionGranted -> {
                getWeatherForecast(latitude = forecastUiEvent.lat, longitude = forecastUiEvent.lng)
                getHistory()
            }

            is ForecastUiEvent.ImageCaptured -> {
                _capturedImageUri.value = forecastUiEvent.uri
            }

            is ForecastUiEvent.ImageSaved -> {
                saveCapturedImageUri(forecastUiEvent.uri)
            }

            ForecastUiEvent.HistorySelected -> {
                getHistory()
            }
        }
    }

    private fun getHistory() {
        viewModelScope.launch {
            _imagesHistory.value = fetchCapturedImages()
        }
    }

    private fun saveCapturedImageUri(uri: String) {
        viewModelScope.launch {
            saveImageUri(uri)
            _capturedImageUri.value = null
        }
    }

    private fun getWeatherForecast(
        latitude: Double,
        longitude: Double,
        exclude: String = "minutely"
    ) {
        viewModelScope.launch {
            when (val result = getWeatherForecastUseCase.invoke(
                latitude.toString(),
                longitude.toString(),
                exclude
            )) {
                is ResultState.Error -> {
                    _forecastUiState.value = ForecastUiState.Failure(result.errorMessage)
                }

                is ResultState.Success -> {
                    _forecastUiState.value = ForecastUiState.Success(result.body)
                }
            }
        }
    }

}