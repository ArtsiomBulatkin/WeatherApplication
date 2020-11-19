package com.example.weatherapplication.model

import com.google.gson.annotations.SerializedName

data class WeatherListModel(
    val list: List<WeatherModel>
) {

    data class WeatherModel(
        val city: City,
        @SerializedName("main")
        val mainTemp: Main,
        @SerializedName("dt_txt")
        var dateTime: String,
        val weather: Weather
    )

    data class City(
        @SerializedName("name")
        val city: String
    )

    data class Main(
        val temp: String
    )

    data class Weather(
        val description: String,
        val icon: String
    )

}