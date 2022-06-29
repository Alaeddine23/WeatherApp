package com.octo.weatherapp.mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.ForecastInteractor
import com.example.domain.WeatherNetworkRepositoryInterface
import com.example.domain.WeatherWeeklyForecastModel
import com.example.presentation.WeatherWeeklyForecastDisplayModel

class WeatherViewModel : ViewModel() {
    lateinit var interactor: ForecastInteractor

    var weatherWeeklyForecastModelLiveData =
        MutableLiveData<WeatherWeeklyForecastDisplayModel>()

    var errorModelLiveData = MutableLiveData<Boolean>()

    suspend fun loadWeatherWeeklyForecastModel(cityName: String) {
        when (val response = interactor.loadForecastForViewModel(cityName = cityName)) {
            is WeatherNetworkRepositoryInterface.WeatherWeeklyForecastResponse.Success -> {
                errorModelLiveData.postValue(false)
                weatherWeeklyForecastModelLiveData.postValue(response.model.toDisplayModel())
            }
            WeatherNetworkRepositoryInterface.WeatherWeeklyForecastResponse.Failure -> errorModelLiveData.postValue(
                true
            )
        }
    }
}

private fun WeatherWeeklyForecastModel.toDisplayModel(): WeatherWeeklyForecastDisplayModel {
    return WeatherWeeklyForecastDisplayModel(
        cityName = this.cityName,
        temperatureMin = "${minForecast.temperature}°C",
        temperatureMax = "${maxForecast.temperature}°C"
    )
}