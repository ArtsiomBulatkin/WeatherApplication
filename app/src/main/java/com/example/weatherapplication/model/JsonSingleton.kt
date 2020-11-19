package com.example.weatherapplication.model

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object JsonSingleton {
    val api: ApiClient = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiClient::class.java)
}