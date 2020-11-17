package com.example.weatherapplication.model

import io.reactivex.Single

interface WeatherRepository {
    fun geCurrentWeather(city: String): Single<CurrentWeatherModel>
    fun getWeatherList(city: String): Single<WeatherListModel>
}