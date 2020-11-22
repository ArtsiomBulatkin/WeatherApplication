package com.example.weatherapplication.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.weatherapplication.R
import com.example.weatherapplication.utils.Constants
import kotlinx.android.synthetic.main.activity_weather.*

class WeatherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        val currentWeatherFragment = CurrentWeatherFragment()
        val weatherListFragment = WeatherListFragment()
        //val fragmentManager = supportFragmentManager
        var active : Fragment = currentWeatherFragment

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainer, weatherListFragment, Constants.LIST_FRAG_TAG)
            .hide(weatherListFragment)
            .commit()

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainer, currentWeatherFragment, Constants.CURRENT_FRAG_TAG)
            .commit()

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_item1 -> {
                    supportFragmentManager
                        .beginTransaction()
                        .hide(active)
                        .show(currentWeatherFragment)
                        .commit()
                    active = currentWeatherFragment
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.action_item2 -> {
                    supportFragmentManager
                        .beginTransaction()
                        .hide(active)
                        .show(weatherListFragment)
                        .commit()
                    active = weatherListFragment
                    return@setOnNavigationItemSelectedListener true               }
                else -> false
            }
        }
    }

}