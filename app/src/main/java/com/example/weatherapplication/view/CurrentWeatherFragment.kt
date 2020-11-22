package com.example.weatherapplication.view

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat

import androidx.fragment.app.Fragment
import com.example.weatherapplication.R
import com.example.weatherapplication.model.CurrentWeatherModel
import com.example.weatherapplication.model.WeatherListModel
import com.example.weatherapplication.presenter.WeatherInfoPresenter
import com.example.weatherapplication.utils.Constants
import com.example.weatherapplication.utils.GpsUtil
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


class CurrentWeatherFragment : Fragment(), ViewContract {

    private lateinit var presenter: WeatherInfoPresenter
    private var isGPSEnable = false

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var lat = Constants.DEFAULT_LAT
    private var lon = Constants.DEFAULT_LON

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GpsUtil(requireContext()).turnGPSOn(object : GpsUtil.OnGpsListener {
            override fun gpsStatus(isGPSEnabled: Boolean) {
                this@CurrentWeatherFragment.isGPSEnable = isGPSEnable
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        inflater.inflate(R.layout.fragment_current_weather, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)


        presenter = WeatherInfoPresenter(this)

    }


    private fun invokeLocation() {
        when {
            allPermissionsGranted() -> {
                getLocation()
                presenter.loadDataCurrentWeather(lat, lon)

            }
            showRequestPermissionRationale() -> {
                AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.location_permission))
                    .setMessage(getString(R.string.access_location_message))
                    .setNegativeButton(getString(R.string.no)) { _, _ -> requireActivity().finish() }
                    .setPositiveButton(getString(R.string.ask_me)) { _, _ ->
                        requestPermissions(PERMISSION, Constants.PERMISSINS_REQUEST)
                    }
                    .show()
            }
            !isGPSEnable -> {
                Toast.makeText(
                    context,
                    R.string.gps_required_message,
                    Toast.LENGTH_LONG
                )
                    .show()
            }
            else -> {
                requestPermissions(PERMISSION, Constants.PERMISSINS_REQUEST)
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                lat = location?.latitude.toString()
                lon = location?.longitude.toString()
            }
    }

    private fun allPermissionsGranted() = PERMISSION.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    private fun showRequestPermissionRationale() = PERMISSION.all {
        shouldShowRequestPermissionRationale(it)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.PERMISSINS_REQUEST) {
            invokeLocation()
        }
    }


    override fun loadCurrentWeatherView(currentWeatherModel: CurrentWeatherModel) {
//        if (currentWeatherModel != null){
//
//        }
    }

    override fun loadListWeatherView(weatherListModel: WeatherListModel) {

    }


    companion object {
        private val PERMISSION = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }
}