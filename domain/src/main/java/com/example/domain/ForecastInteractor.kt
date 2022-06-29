package com.example.domain

import com.example.domain.WeatherNetworkRepositoryInterface.WeatherWeeklyForecastResponse.Failure
import com.example.domain.WeatherNetworkRepositoryInterface.WeatherWeeklyForecastResponse.Success
import java.util.*

class ForecastInteractor(
    private val repository: WeatherNetworkRepositoryInterface,
    private val weatherNetworkPresenterInterface: WeatherNetworkPresenterInterface
) {

    companion object{
        private const val MIN_FORECASTS = 2
        private const val MIN_HOUR = 8
        private const val MAX_HOUR = 20
    }

    suspend fun loadForecast(cityName: String) {
        when (val result = repository.loadCityForecast(cityName = cityName)) {
            is Success -> {
                if (getRelevantForecasts(result.model.forecasts).size < MIN_FORECASTS) {
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
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            hour in MIN_HOUR..MAX_HOUR
        }
}