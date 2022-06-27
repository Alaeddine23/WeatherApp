package com.octo.data

data class JsonCityWeeklyForecast(
    val jsonCity: JsonCity,
    val jsonForecasts: List<JsonForecast>
)

data class JsonCity(
    val name: String
)

data class JsonMain(
    val temperature: Float
)

data class JsonForecast(
    val jsonMain: JsonMain,
    val date: String
)