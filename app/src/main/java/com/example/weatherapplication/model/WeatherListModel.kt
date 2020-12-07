package com.example.weatherapplication.model

import com.google.gson.annotations.SerializedName

data class WeatherListModel(
    val list: List<WeatherModel>,
    val city: City
) {

    data class WeatherModel(
        val main: Main,
        @SerializedName("dt_txt")
        var dateTime: String,
        val weather: List<Weather>
    )

    data class City(
        @SerializedName("name")
        val city: String
    )

    data class Main(
        val temp: String,
        val pressure: String,
        val humidity: String
    )

    data class Weather(
        @SerializedName("main")
        val description: String,
        val icon: String
    )
}