package com.example.weatherapplication.presenter

import android.content.Context

class PresenterContract {

    interface CurrentWeatherContract {
        fun loadDataCurrentWeather(lat: String, lon: String)
        fun dispose()
    }

    interface WeatherListContract {

        fun loadLocation(context: Context)
        fun loadDataListWeather(lat: String, lon: String)
        fun dispose()
    }


}