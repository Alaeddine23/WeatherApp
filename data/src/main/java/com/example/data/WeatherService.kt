package com.example.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("forecast/hourly")
    suspend fun getCityWeeklyForecast(
        @Query("q") cityName: String,
        @Query("appid") apiKey : String = "ec3b4790643f7d21ceb6cc1d9e2d8a8e",
        @Query("units") units : String = "metric"
    ) : Response<JsonCityWeeklyForecast>

}