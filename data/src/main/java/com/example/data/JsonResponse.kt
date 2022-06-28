package com.example.data

data class JsonCity(
    val name: String,
)

data class JsonMain(
    val temp: Float,
    val tempMin: Float,
    val tempMax: Float,
)

data class JsonForecast(
    val jsonMain: JsonMain,
    val date: Long
)

data class JsonCityWeeklyForecast(
    val jsonCity: JsonCity,
    val forecasts: List<JsonForecast>,
)