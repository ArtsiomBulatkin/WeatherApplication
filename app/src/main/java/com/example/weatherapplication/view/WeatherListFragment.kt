package com.example.weatherapplication.view

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.weatherapplication.R
import com.example.weatherapplication.model.LocationModel
import com.example.weatherapplication.model.WeatherListModel
import com.example.weatherapplication.presenter.WeatherListPresenter
import com.example.weatherapplication.utils.Constants
import com.example.weatherapplication.utils.SharedPreferenceManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.fragment_weather_list.*
import timber.log.Timber

class WeatherListFragment : Fragment(), ViewContract.WeatherListView {

   private lateinit var presenter: WeatherListPresenter
   private lateinit var prefs: SharedPreferenceManager
   private lateinit var locationModel: LocationModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var lat = Constants.DEFAULT_LAT
    private var lon = Constants.DEFAULT_LON

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = SharedPreferenceManager.getInstance(requireContext())

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
      return  inflater.inflate(R.layout.fragment_weather_list, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = WeatherListPresenter(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        getLocation()
  //      loadWeatherList()


    }
    @SuppressLint("MissingPermission")
    private fun getLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                lat = location?.latitude.toString()
                lon = location?.longitude.toString()
                //locationModel = LocationModel(lat, lon)
               // prefs.saveLocation(locationModel)
                presenter.loadDataListWeather(lat, lon)
            }
    }

    override fun loadListWeatherView(weatherListModel: WeatherListModel) {

        textView.text = weatherListModel.list[1].mainTemp.temp
        //textView2.text = weatherListModel.list[0].mainTemp.temp
       // textView3.text = weatherListModel.list[0].city.city
    }

    override fun loadErrorMessage(message: String) {
        Timber.e(message)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dispose()
    }

//    private fun loadWeatherList(){
//        locationModel = prefs.getLocation()
//
//        lat = locationModel.latitude
//        lon = locationModel.longitude
//        presenter.loadDataListWeather(lat, lon)
//    }
}