package com.example.data

import com.fasterxml.jackson.annotation.JsonProperty

data class JsonCity(
    @JsonProperty("name")
    val cityName: String
)

data class JsonMain(
    @JsonProperty("temp")
    val temp: Float
)

data class JsonForecast(
    @JsonProperty("main")
    val jsonMain: JsonMain,
    @JsonProperty("dt_txt")
    val date: String
)

data class JsonCityWeeklyForecast (
    @JsonProperty("city")
    val jsonCity: JsonCity,
    @JsonProperty("list")
    val jsonForecasts: List<JsonForecast>
)
