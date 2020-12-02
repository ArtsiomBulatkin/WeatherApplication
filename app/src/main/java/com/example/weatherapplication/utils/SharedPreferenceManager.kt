package com.example.weatherapplication.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.weatherapplication.model.LocationModel

class SharedPreferenceManager {

    fun setLocation(context: Context, location: LocationModel) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(Constants.LOCATION, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(Constants.SAVE_LAT_TAG, location.latitude)
        editor.putString(Constants.SAVE_LON_TAG, location.longitude)
        editor.apply()
    }

    fun getLocation(context: Context): LocationModel {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(Constants.LOCATION, Context.MODE_PRIVATE)
        return LocationModel(
            sharedPreferences.getString(Constants.SAVE_LAT_TAG, null),
            sharedPreferences.getString(Constants.SAVE_LON_TAG, null)
        )
    }
}