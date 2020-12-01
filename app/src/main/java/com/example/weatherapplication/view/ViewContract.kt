package com.example.weatherapplication.view

import com.example.weatherapplication.model.CurrentWeatherModel
import com.example.weatherapplication.model.WeatherListModel
import com.example.weatherapplication.view.adapter.Item

class ViewContract {

    interface CurrentWeatherView {
        fun loadCurrentWeatherView(currentWeatherModel: CurrentWeatherModel)
        fun loadErrorMessage(message: String)
        fun shareText()
    }

    interface WeatherListView {

        fun loadLocation(lat: String?, lon: String?)
        fun loadListWeatherView(list: List<Item>)
        fun loadErrorMessage(message: String)

    }


}