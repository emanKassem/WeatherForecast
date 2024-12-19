package com.golyv.forecast.presentation.model

sealed class ForecastUiEvent {
    data class PermissionGranted(val lat: Double, val lng: Double): ForecastUiEvent()
    data object PermissionDenied: ForecastUiEvent()
    data class ImageSaved(val uri: String): ForecastUiEvent()
    data class ImageCaptured(val uri: String): ForecastUiEvent()
    data object HistorySelected: ForecastUiEvent()
}