package com.example.weatherapplication.model

import io.reactivex.Single


class WeatherRepository {

    private val api = JsonSingleton.api

    fun getWeather(
        latitude: String,
        longitude: String
    ): Single<CurrentWeatherModel> {
        return api.getCurrentWeather(latitude, longitude)
    }

    fun getWeatherList(
        latitude: String,
        longitude: String
    ): Single<WeatherListModel> {
        return api.getListWeather(latitude, longitude)
    }


}