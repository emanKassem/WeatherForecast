package com.golyv.forecast.data.di

import com.golyv.database.CapturedImageDao
import com.golyv.forecast.data.api.ForecastApi
import com.golyv.forecast.data.datasource.WeatherForecastDataSource
import com.golyv.forecast.data.datasource.WeatherForecastDataSourceImp
import com.golyv.forecast.data.repository.WeatherForecastRepoImp
import com.golyv.forecast.domain.repository.WeatherForecastRepo
import com.golyv.network.retrofit.ApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class ForecastModule {

    @Provides
    fun provideForecastApi(): ForecastApi = ApiClient.getClient().create(ForecastApi::class.java)

    @Provides
    fun provideForecastDataSource(
        forecastApi: ForecastApi,
        capturedImageDao: CapturedImageDao
    ): WeatherForecastDataSource =
        WeatherForecastDataSourceImp(forecastApi, capturedImageDao)

    @Provides
    fun provideForecastRepo(weatherForecastDataSource: WeatherForecastDataSource): WeatherForecastRepo =
        WeatherForecastRepoImp(weatherForecastDataSource)
}