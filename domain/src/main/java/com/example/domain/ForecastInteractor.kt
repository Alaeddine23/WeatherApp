package com.example.domain

import com.example.domain.WeatherNetworkRepositoryInterface.WeatherWeeklyForecastResponse.*
import java.time.LocalDate
import java.util.*

class ForecastInteractor(
    private val repository: WeatherNetworkRepositoryInterface,
    private val weatherNetworkPresenterInterface: WeatherNetworkPresenterInterface
) {
    fun loadForecast(cityName: String) {
        when (val result = repository.loadCityForecast(cityName = cityName)) {
            is Success -> {
                if (getRelevantForecasts(result.model.forecasts).size < 2) {
                    weatherNetworkPresenterInterface.presentOnFailure()
                } else {
                    weatherNetworkPresenterInterface.presentOnSuccess(result.model)
                }
            }
            Failure -> weatherNetworkPresenterInterface.presentOnFailure()
        }
    }

    private fun getRelevantForecasts(forecasts: List<ForecastModel>): List<ForecastModel> =
        forecasts.filter { forecastModel ->
            val calendar = Calendar.getInstance()
            calendar.time = forecastModel.date
            val hour = calendar.get(Calendar.HOUR)
            hour in 8..20
        }
}