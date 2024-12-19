package com.golyv.forecast.data.datasource

import com.golyv.database.CapturedImageDao
import com.golyv.database.CapturedImageEntity
import com.golyv.forecast.data.api.ForecastApi
import com.golyv.forecast.data.model.WeatherForecastResponseModel
import com.golyv.network.model.ResultState
import com.golyv.network.util.APIKey.API_KEY

class WeatherForecastDataSourceImp(
    private val forecastApi: ForecastApi,
    private val capturedImageDao: CapturedImageDao
) : WeatherForecastDataSource {

    override suspend fun getWeatherForecast(
        latitude: String,
        longitude: String,
        exclude: String
    ): ResultState<WeatherForecastResponseModel> =
        try {
            val data = forecastApi.getOneCall(
                lat = latitude,
                lon = longitude,
                exclude = exclude,
                units = "metric",
                API_KEY
            )
            ResultState.Success(body = data)
        } catch (e: Exception) {
            ResultState.Error(errorMessage = "Connectivity issue!")
        }

    override suspend fun saveImageUri(imageUri: String) {
        capturedImageDao.insertString(CapturedImageEntity(uri = imageUri))
    }

    override suspend fun fetchCapturedImages() =
        capturedImageDao.getAllCapturedImages()

}