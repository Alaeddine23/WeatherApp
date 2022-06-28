package com.example.data

import com.google.gson.annotations.SerializedName

data class JsonCity(
    @SerializedName("name")
    val cityName: String
)

data class JsonMain(
    @SerializedName("temp")
    val temp: Float
)

data class JsonForecast(
    @SerializedName("main")
    val jsonMain: JsonMain,
    @SerializedName("dt_txt")
    val date: String
)

data class JsonCityWeeklyForecast (
    @SerializedName("city")
    val jsonCity: JsonCity?,
    @SerializedName("list")
    val jsonForecasts: List<JsonForecast>?
)
