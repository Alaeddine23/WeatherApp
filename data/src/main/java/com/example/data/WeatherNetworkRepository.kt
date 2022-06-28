package com.example.data

import retrofit2.Response
import retrofit2.Retrofit

class WeatherNetworkRepository(private val retrofit: Retrofit) {
    suspend fun loadCityForecasts(cityName: String, apiKey: String, unit: String): Response<JsonCityWeeklyForecast>? {
        val service = retrofit.create(WeatherService::class.java)
        val response = service.getJsonCityWeeklyForecast(cityName, apiKey, unit)
        return if (response.isSuccessful)
            response
        else
            null
    }
}