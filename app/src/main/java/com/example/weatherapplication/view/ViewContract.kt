package com.example.weatherapplication.view

import com.example.weatherapplication.model.CurrentWeatherModel
import com.example.weatherapplication.model.WeatherListModel

interface ViewContract {
    fun loadCurrentWeatherView(currentWeatherModel: CurrentWeatherModel)
    fun loadListWeatherView(weatherListModel: WeatherListModel)

}