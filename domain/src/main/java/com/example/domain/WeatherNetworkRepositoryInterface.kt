package com.example.domain

interface WeatherNetworkRepositoryInterface {

    sealed class WeatherWeeklyForecastResponse {
        data class Success(val model: WeatherWeeklyForecastModel) : WeatherWeeklyForecastResponse()
        object Failure : WeatherWeeklyForecastResponse()
    }

    suspend fun loadCityForecast(cityName: String): WeatherWeeklyForecastResponse
}

