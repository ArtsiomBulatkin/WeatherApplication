package com.example.weatherapplication.model

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody

class ApiClient : WeatherRepository {

    private val okHttpClient = OkHttpClient()
    private val currentWeatherModel : (String) -> CurrentWeatherModel = CurrentWeatherModelMapper()

    override fun geCurrentWeather(city: String): Single<CurrentWeatherModel> {
        val url = "api.openweathermap.org/data/2.5/weather?q=$city&appid=$API_KEY"
        val request = Request.Builder()
            .url(url)
            .build()

        return Single.create<String> { emitter ->
            okHttpClient.newCall(request).execute().use { response ->
                if (response.body() == null) emitter.onError(NullPointerException("null"))
                if (!response.isSuccessful) emitter.onError(Throwable(response.code().toString()))
                emitter.onSuccess((response.body() as ResponseBody).string())
            }
        }
            .subscribeOn(Schedulers.io())
            .map { json -> currentWeatherModel(json) }
    }

    override fun getWeatherList(city: String): Single<WeatherListModel> {
        TODO("Not yet implemented")
    }

    companion object {
        const val API_KEY = "4f25122ce401ebd87e4cb86b33627f93"
    }
}