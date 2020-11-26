package com.example.weatherapplication.view

import com.example.weatherapplication.model.CurrentWeatherModel
import com.example.weatherapplication.model.WeatherListModel

class ViewContract {

    interface CurrentWeatherView {
        fun loadCurrentWeatherView(currentWeatherModel: CurrentWeatherModel)
        fun loadErrorMessage(message: String)
        fun shareText()
    }

    interface WeatherListView {

        fun loadLocation(lat: String?, lon: String?)
        fun loadListWeatherView(weatherListModel: WeatherListModel)
        fun loadErrorMessage(message: String)

    }


}