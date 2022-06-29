package com.example.presentation

import com.example.domain.WeatherNetworkPresenterInterface
import com.example.domain.WeatherWeeklyForecastModel

class WeatherWeeklyForecastPresenter(
    private val weatherWeeklyForecastView: WeatherWeeklyForecastView
) : WeatherNetworkPresenterInterface {

    override fun presentOnSuccess(model: WeatherWeeklyForecastModel) {
        weatherWeeklyForecastView.displayForecasts(model.toDisplayModel())
    }

    override fun presentOnFailure() {
        weatherWeeklyForecastView.displayError()
    }
}

private fun WeatherWeeklyForecastModel.toDisplayModel(): WeatherWeeklyForecastDisplayModel{
    return WeatherWeeklyForecastDisplayModel(
        cityName = this.cityName,
        temperatureMin = "${minForecast.temperature}°C",
        temperatureMax = "${maxForecast.temperature}°C"
    )
}