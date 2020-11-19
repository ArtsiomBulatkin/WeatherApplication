package com.example.weatherapplication.model

import com.google.gson.annotations.SerializedName

data class CurrentWeatherModel(
    @SerializedName("name")
    val city: String,
    val weather: Weather,
    val main: Main,
    val sys: Sys,
    val wind: Wind
) {

    data class Weather(
        @SerializedName("main")
        val description: String,
        val icon: String
    )

    data class Main(
        val temp: String,
        val pressure: String,
        val humidity: String
    )

    data class Sys(
        val country: String
    )

    data class Wind(
        val wind: String,
        val deg: Int
    )
}