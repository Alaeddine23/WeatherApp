package com.octo.weatherapp.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.ForecastInteractor
import com.example.domain.WeatherNetworkRepositoryInterface
import com.example.domain.WeatherWeeklyForecastModel
import com.example.presentation.WeatherWeeklyForecastDisplayModel
import com.octo.weatherapp.ForecastModule
import kotlinx.coroutines.launch

class WeatherWeeklyViewModel : ViewModel() {

    private val interactor: ForecastInteractor = ForecastModule.provideInteractor()

    private val successLiveDataMutable = MutableLiveData<WeatherWeeklyForecastDisplayModel>()
    val successLiveData: LiveData<WeatherWeeklyForecastDisplayModel> = successLiveDataMutable

    private val failedLiveDataMutable = MutableLiveData<Boolean>()
    val failedLiveData: LiveData<Boolean> = failedLiveDataMutable

    fun loadForecast(cityName: String) {
        viewModelScope.launch {
            when (val result = interactor.loadForecast(cityName)) {
                WeatherNetworkRepositoryInterface.WeatherWeeklyForecastResponse.Failure ->
                    failedLiveDataMutable.postValue(true)
                is WeatherNetworkRepositoryInterface.WeatherWeeklyForecastResponse.Success -> {
                    val displayModel = result.model.toDisplayModel()
                    failedLiveDataMutable.postValue(false)
                    successLiveDataMutable.postValue(displayModel)
                }
            }
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