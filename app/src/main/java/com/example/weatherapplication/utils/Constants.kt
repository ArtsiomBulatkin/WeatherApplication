package com.example.weatherapplication.utils

import android.Manifest

object Constants {
    const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    const val API_KEY = "4f25122ce401ebd87e4cb86b33627f93"
    const val CURRENT_FRAG_TAG = "current"
    const val LIST_FRAG_TAG = "list"

    const val PERMISSIONS_REQUEST: Int = 99

    const val DEFAULT_LAT = "51.509865"
    const val DEFAULT_LON = "-0.118092"
    const val GPS_REQUEST_CHECK_SETTINGS = 98
}