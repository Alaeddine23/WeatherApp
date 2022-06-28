package com.example.data

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class WeatherNetworkRepository(
    private val networkModule: NetworkModule
) {

    fun loadCityForecasts(cityName: String, apiKey: String, unit: String): JsonCityWeeklyForecast? {

        var result: JsonCityWeeklyForecast? = null
        GlobalScope.launch {
            result = networkModule.provideWeatherService()
                .getJsonCityWeeklyForecast(cityName, apiKey, unit)
                .body()
        }
        return result
    }
}