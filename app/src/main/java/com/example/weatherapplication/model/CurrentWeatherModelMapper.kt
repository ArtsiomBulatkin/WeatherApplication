package com.example.weatherapplication.model

import org.json.JSONObject

class CurrentWeatherModelMapper : (String) -> CurrentWeatherModel {

    override fun invoke(json: String): CurrentWeatherModel {

        val jsonObj = JSONObject(json)
        val country = jsonObj.getString("country")
        val city = jsonObj.getString("name")
        val temp = jsonObj.getJSONObject("main").getString("temp")
        val windSpeed = jsonObj.getJSONObject("wind").getString("speed")
        val wind = jsonObj.getJSONObject("wind").getString("deg")
        val pressure = jsonObj.getJSONObject("main").getString("pressure")
        val humidity = jsonObj.getJSONObject("main").getString("humidity")
        val description = jsonObj.getJSONArray("weather").getJSONObject(0).getString("icon")
        val iconName = jsonObj.getJSONArray("weather").getJSONObject(0).getString("icon")
        val icon = "http://openweathermap.org/img/wn/$iconName@2x.png"

        return CurrentWeatherModel(
            country,
            city,
            temp,
            windSpeed,
            wind,
            pressure,
            humidity,
            description,
            icon
        )
    }

}