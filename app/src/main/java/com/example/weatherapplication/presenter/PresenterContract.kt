package com.example.weatherapplication.presenter

interface PresenterContract {
    fun loadDataCurrentWeather(lat: String, lon: String)
    fun loadDataListWeather(lat: String, lon: String)
    fun dispose()
}