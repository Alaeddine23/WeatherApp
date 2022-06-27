package com.octo.data.network

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("forecast")
    fun getCityWeeklyForecast(
        @Query("q") town: String,
        @Query("appId") apiKey: String,
        @Query("units") units: String,
    )
}