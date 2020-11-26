package com.example.weatherapplication.presenter

import android.content.Context
import com.example.weatherapplication.model.LocationModel
import com.example.weatherapplication.model.WeatherRepository
import com.example.weatherapplication.utils.SharedPreferenceManager
import com.example.weatherapplication.view.ViewContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class WeatherListPresenter(private val view: ViewContract.WeatherListView) :
    PresenterContract.WeatherListContract {

    private val repo = WeatherRepository()
    private val disposable = CompositeDisposable()
    private lateinit var prefs: SharedPreferenceManager
    private lateinit var locationModel: LocationModel


    override fun loadLocation(context: Context) {
        prefs = SharedPreferenceManager()
        locationModel = prefs.getLocation(context)
        view.loadLocation(locationModel.latitude, locationModel.longitude)

    }

    override fun loadDataListWeather(lat: String, lon: String) {
        disposable.add(
            repo.getWeatherList(lat, lon)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ listWeather -> view.loadListWeatherView(listWeather) },
                    { throwable -> view.loadErrorMessage("CurrentList Error occurred: $throwable") })

        )
    }


    override fun dispose() {
        disposable.dispose()
    }
}