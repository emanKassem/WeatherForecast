package com.golyv.forecast.data.api

import com.golyv.forecast.data.model.WeatherForecastResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastApi {

    @GET("data/3.0/onecall")
    suspend fun getOneCall(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("exclude") exclude:String,
        @Query("units") units:String,
        @Query("appid") apiKey: String
    ): WeatherForecastResponseModel

}

