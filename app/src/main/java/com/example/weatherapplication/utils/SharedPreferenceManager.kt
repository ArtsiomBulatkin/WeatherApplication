package com.example.weatherapplication.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.core.content.edit
import com.example.weatherapplication.model.LocationModel
import com.google.gson.Gson

@Suppress("DEPRECATION")
class SharedPreferenceManager {

    fun saveLocation(location: LocationModel) {
        prefs?.edit(commit = true) {
            val gson = Gson()
            val json = gson.toJson(location)
            putString(Constants.LOCATION, json)
        }
    }


    fun getLocation(): LocationModel {
        val gson = Gson()
        val json = prefs?.getString(Constants.LOCATION, null)
        return gson.fromJson(json, LocationModel::class.java)
    }

    companion object {

        private var prefs: SharedPreferences? = null


        @Volatile
        private var sharedPreferenceManager: SharedPreferenceManager? = null

        fun getInstance(context: Context): SharedPreferenceManager {
            synchronized(this) {
                val instance = sharedPreferenceManager
                if (instance == null) {
                    prefs = PreferenceManager.getDefaultSharedPreferences(context)
                    sharedPreferenceManager = instance
                }
                return SharedPreferenceManager()
            }
        }
    }
}