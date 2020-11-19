package com.example.weatherapplication.model

import io.reactivex.Single

import retrofit2.http.GET
import retrofit2.http.Path

interface ApiClient {

    @GET("weather?lat={lat}&lon={lon}&units=metric&mode=json&appid=4f25122ce401ebd87e4cb86b33627f93")
    fun getCurrentWeather(
        @Path("lat") latitude: String,
        @Path("lon") longitude: String
    ): Single<CurrentWeatherModel>

    @GET("forecast?lat={lat}&lon={lon}&units=metric&mode=json&appid=4f25122ce401ebd87e4cb86b33627f93")
    fun getListWeather(
        @Path("lat") latitude: String,
        @Path("lon") longitude: String
    ): Single<List<WeatherListModel>>
}