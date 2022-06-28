package com.example.data

import retrofit2.Response
import retrofit2.Retrofit

class WeatherNetworkRepository(private val retrofit: Retrofit) {
    companion object {
        private const val API_KEY = "ec3b4790643f7d21ceb6cc1d9e2d8a8e"
        private const val UNIT = "metric"
    }

    suspend fun loadCityForecasts(cityName: String, apiKey: String, unit: String): Response<JsonCityWeeklyForecast>? {
        val service = retrofit.create(WeatherService::class.java)
        val response = service.getJsonCityWeeklyForecast(cityName, apiKey, unit)
        return if (response.isSuccessful)
            response
        else
            null
    }
}