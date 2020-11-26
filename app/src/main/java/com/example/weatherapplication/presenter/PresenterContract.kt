package com.example.weatherapplication.presenter


class PresenterContract {

    interface CurrentWeatherContract {
        fun loadDataCurrentWeather(lat: String, lon: String)
        fun dispose()
    }

    interface WeatherListContract {
        fun loadDataListWeather(lat: String, lon: String)
        fun dispose()
    }

}