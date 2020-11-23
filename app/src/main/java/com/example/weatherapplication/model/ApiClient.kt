package com.example.weatherapplication.model

import com.example.weatherapplication.utils.Constants
import io.reactivex.Single

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiClient {

    @GET("weather?units=metric&mode=json&appid=${Constants.API_KEY}")
    fun getCurrentWeather(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String
    ): Single<CurrentWeatherModel>

    @GET("forecast?units=metric&mode=json&appid=${Constants.API_KEY}")
    fun getListWeather(
        @Query("lat") latitude: String,
        @Query("lon") longitude: String
    ): Single<WeatherListModel>
}