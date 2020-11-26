package com.example.weatherapplication.view

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat

import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.weatherapplication.R
import com.example.weatherapplication.model.CurrentWeatherModel
import com.example.weatherapplication.model.LocationModel
import com.example.weatherapplication.presenter.CurrentWeatherPresenter
import com.example.weatherapplication.utils.*
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.fragment_current_weather.*
import timber.log.Timber


class CurrentWeatherFragment : Fragment(), ViewContract.CurrentWeatherView {

    private lateinit var presenter: CurrentWeatherPresenter
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var isCheckGPSEnable = false

    private var lat = Constants.DEFAULT_LAT
    private var lon = Constants.DEFAULT_LON
    private lateinit var prefs: SharedPreferenceManager
    private lateinit var textShare: String
    private lateinit var locationModel: LocationModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = SharedPreferenceManager()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        GpsUtil(requireContext()).turnGPSOn(object : GpsUtil.OnGpsListener {
            override fun gpsStatus(isGPSEnabled: Boolean) {
                this@CurrentWeatherFragment.isCheckGPSEnable = isGPSEnabled
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

        presenter = CurrentWeatherPresenter(this)
        invokeLocation()
        shareTextButton.setOnClickListener {
            presenter.onShareText()
        }


    }


    private fun invokeLocation() {
        when {
            allPermissionsGranted() -> {
                getLocation()
            }
            showRequestPermissionRationale() -> {
                AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.location_permission))
                    .setMessage(getString(R.string.access_location_message))
                    .setNegativeButton(getString(R.string.no)) { _, _ ->
                        locationModel = LocationModel(lat, lon)
                        prefs.setLocation(requireContext(),locationModel)
                        presenter.loadDataCurrentWeather(
                            lat,
                            lon
                        )
                    }
                    .setPositiveButton(getString(R.string.yes)) { _, _ ->
                        requestPermissions(PERMISSION, Constants.PERMISSIONS_REQUEST)
                        getLocation()
                    }
                    .show()
            }
            !isCheckGPSEnable -> {
                Toast.makeText(
                    context,
                    R.string.gps_required_message,
                    Toast.LENGTH_LONG
                )
                    .show()
            }
            else -> {
                requestPermissions(PERMISSION, Constants.PERMISSIONS_REQUEST)
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                lat = location?.latitude.toString()
                lon = location?.longitude.toString()
                locationModel = LocationModel(lat, lon)
                prefs.setLocation(requireContext(),locationModel)
                presenter.loadDataCurrentWeather(lat, lon)
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
        if (requestCode == Constants.PERMISSIONS_REQUEST) {
            getLocation()

        }
    }


    override fun loadCurrentWeatherView(currentWeatherModel: CurrentWeatherModel) {

        val iconName = currentWeatherModel.weather[0].icon
        val iconUrl = "https://openweathermap.org/img/wn/$iconName@2x.png"
        Glide.with(requireActivity()).load(iconUrl).into(weatherImageView)

        locationTextView.text = "${currentWeatherModel.city}, ${currentWeatherModel.sys.country}"
        val temp = roundData(currentWeatherModel.main.temp)
        val weatherDescription = currentWeatherModel.weather[0].description
        tempTextView.text = "$temp°С | $weatherDescription"
        humidityTextView.text = "${currentWeatherModel.main.humidity} %"
        val visibility = meterToKm(currentWeatherModel.visibility)
        visibilityTextView.text = "$visibility km"
        pressureTextView.text = "${currentWeatherModel.main.pressure} hPa"
        val speed = roundData(currentWeatherModel.wind.speed)
        speedTextView.text = "$speed km/h"
        val wind = windToDescription(currentWeatherModel.wind.deg)
        windDescriptionTextView.text = wind
        textShare =
            "The current weather today in ${currentWeatherModel.city}: $weatherDescription, $temp°С, humidity ${humidityTextView.text}, visibility ${visibilityTextView.text}, wind speed ${speedTextView.text}, direction of the wind ${windDescriptionTextView.text}"
    }


    override fun loadErrorMessage(message: String) {
        Timber.e(message)
    }

    override fun shareText() {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, textShare)
        sendIntent.type = "text/plain"
        Intent.createChooser(sendIntent, "Share weather")
        startActivity(sendIntent)

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dispose()
    }

    companion object {
        private val PERMISSION = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }
}


