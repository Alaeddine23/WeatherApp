package com.example.domain

interface WeatherNetworkRepositoryInterface {

    sealed class WeatherWeeklyForecastResponse {
        class Success(val model: WeatherWeeklyForecastModel) : WeatherWeeklyForecastResponse()
        object Failure : WeatherWeeklyForecastResponse()
    }

    fun loadCityForecast(cityName: String): WeatherWeeklyForecastResponse
}

