package com.example.weatherapplication.model

data class CurrentWeatherModel(
    var country: String,
    var city: String,
    var temp: String,
    var windSpeed: String,
    var wind: String,
    var pressure : String,
    var humidity : String,
    var description : String,
    var icon : String
    )