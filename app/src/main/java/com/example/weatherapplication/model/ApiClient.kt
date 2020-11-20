package com.example.weatherapplication.model

import com.example.weatherapplication.utils.Constants
import io.reactivex.Single

import retrofit2.http.GET
import retrofit2.http.Path

interface ApiClient {

    @GET("weather?lat={lat}&lon={lon}&units=metric&mode=json&appid=${Constants.API_KEY}")
    fun getCurrentWeather(
        @Path("lat") latitude: String,
        @Path("lon") longitude: String
    ): Single<CurrentWeatherModel>

    @GET("forecast?lat={lat}&lon={lon}&units=metric&mode=json&appid=${Constants.API_KEY}")
    fun getListWeather(
        @Path("lat") latitude: String,
        @Path("lon") longitude: String
    ): Single<WeatherListModel>
}