package com.example.weatherapplication.view

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.weatherapplication.R
import com.example.weatherapplication.model.CurrentWeatherModel
import com.example.weatherapplication.model.LocationModel
import com.example.weatherapplication.presenter.CurrentWeatherPresenter
import com.example.weatherapplication.utils.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_current_weather.*
import timber.log.Timber

class CurrentWeatherFragment : Fragment(), ViewContract.CurrentWeatherView {

    private lateinit var presenter: CurrentWeatherPresenter
    private lateinit var lat: String
    private lateinit var lon: String
    private lateinit var prefs: SharedPreferenceManager
    private lateinit var textShare: String
    private lateinit var locationModel: LocationModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = SharedPreferenceManager()
        textShare = getString(R.string.text_share)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_current_weather, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar.visibility = View.VISIBLE
        presenter = CurrentWeatherPresenter(this)
        invokeLocation()
        shareTextButton.setOnClickListener {
            presenter.onShareText()
        }
    }

    private fun invokeLocation() {
        when {
            !NetworkHelper.isNetworkAvailable(requireContext()) -> {
                progressBar.visibility = View.GONE
                Toast.makeText(context, R.string.internet_required_message, Toast.LENGTH_LONG)
                    .show()
            }
            allPermissionsGranted() -> {
                getLocation()
            }
            showRequestPermissionRationale() -> {
                AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.location_permission))
                    .setMessage(getString(R.string.access_location_message))
                    .setNegativeButton(getString(R.string.no)) { _, _ ->
                        Toast.makeText(
                            context,
                            R.string.internet_required_message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    .setPositiveButton(getString(R.string.yes)) { _, _ ->
                        requestPermissions(PERMISSION, Constants.PERMISSIONS_REQUEST)
                        getLocation()
                    }
                    .show()
            }
            else -> {
                requestPermissions(PERMISSION, Constants.PERMISSIONS_REQUEST)
            }
        }
    }

    private fun getLocation() {
        LocationHelper.getInstance(requireContext()).observe(this, Observer { location ->
            if (location != null) {
                lat = location.latitude.toString()
                lon = location.longitude.toString()
                locationModel = LocationModel(lat, lon)
                prefs.setLocation(requireContext(), locationModel)
                presenter.loadDataCurrentWeather(lat, lon)
            }
        })
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
        Picasso.get().load(iconUrl).into(weatherImageView)
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
        textShare = "The current weather today in ${currentWeatherModel.city}: $weatherDescription, $temp°С, humidity ${humidityTextView.text}, visibility ${visibilityTextView.text}, wind speed ${speedTextView.text}, direction of the wind ${windDescriptionTextView.text}"
        progressBar.visibility = View.GONE
    }

    override fun loadErrorMessage(message: String) {
        Timber.e(message)
    }

    override fun shareText() {
        if (!NetworkHelper.isNetworkAvailable(requireContext())) {
            Toast.makeText(context, R.string.internet_required_message, Toast.LENGTH_LONG).show()
        } else {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT, textShare)
            sendIntent.type = getString(R.string.type)
            Intent.createChooser(sendIntent, getString(R.string.send_intent_title))
            startActivity(sendIntent)
        }
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