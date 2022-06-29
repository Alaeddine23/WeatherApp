package com.example.data

import com.example.domain.ForecastModel
import com.example.domain.WeatherNetworkRepositoryInterface
import com.example.domain.WeatherNetworkRepositoryInterface.WeatherWeeklyForecastResponse
import com.example.domain.WeatherWeeklyForecastModel
import retrofit2.Retrofit
import java.text.SimpleDateFormat
import java.util.*

class WeatherNetworkRepository(private val retrofit: Retrofit) : WeatherNetworkRepositoryInterface {
    companion object {
        private const val API_KEY = "ec3b4790643f7d21ceb6cc1d9e2d8a8e"
        private const val UNIT = "metric"
    }

    override suspend fun loadCityForecast(cityName: String): WeatherWeeklyForecastResponse {
        val service = retrofit.create(WeatherService::class.java)
        val response = service.getJsonCityWeeklyForecast(cityName, API_KEY, UNIT)
        return if (response.isSuccessful)
            response.body()?.let {
                WeatherWeeklyForecastResponse.Success(it.toWeatherWeeklyForecastModel())
            } ?: WeatherWeeklyForecastResponse.Failure
        else
            WeatherWeeklyForecastResponse.Failure
    }

    private fun JsonCityWeeklyForecast.toWeatherWeeklyForecastModel(): WeatherWeeklyForecastModel =
        WeatherWeeklyForecastModel(
            cityName = this.jsonCity?.toStringName() ?: "",
            forecasts = this.jsonForecasts?.map { jsonForecast ->
                jsonForecast.toForecastModel()
            } ?: listOf()
        )

    private fun JsonCity.toStringName() = this.cityName
    private fun JsonMain.toFloat() = this.temp
    private fun JsonForecast.toForecastModel() = ForecastModel(
        temperature = this.jsonMain.toFloat(),
        date = this.date.toDate()
    )

    private fun String.toDate(): Date {
        val parser = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return parser.parse(this) ?: Date()
    }
}