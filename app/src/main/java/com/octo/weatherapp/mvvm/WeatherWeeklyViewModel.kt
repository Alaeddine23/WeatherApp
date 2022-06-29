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

    sealed class UiState {
        data class Success(val weatherWeeklyForecastDisplayModel: WeatherWeeklyForecastDisplayModel) : UiState()
        object Failed : UiState()
    }

    private val interactor: ForecastInteractor = ForecastModule.provideInteractor()

    private val liveDataMutable = MutableLiveData<UiState>()
    val liveData: LiveData<UiState> = liveDataMutable

    fun loadForecast(cityName: String) {
        viewModelScope.launch {
            when (val result = interactor.loadForecast(cityName)) {
                WeatherNetworkRepositoryInterface.WeatherWeeklyForecastResponse.Failure ->
                    liveDataMutable.postValue(UiState.Failed)
                is WeatherNetworkRepositoryInterface.WeatherWeeklyForecastResponse.Success -> {
                    val displayModel = result.model.toDisplayModel()
                    liveDataMutable.postValue(UiState.Success(displayModel))
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