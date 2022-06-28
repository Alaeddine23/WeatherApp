package com.example.data

data class JsonCity(
    val cityName: String
)

data class JsonMain(
    val temp: Float
)

data class JsonForecast(
    val jsonMain: JsonMain,
    val date: String
)

data class JsonCityWeeklyForecast (
    val jsonCity: JsonCity,
    val jsonForecasts: List<JsonForecast>
)
