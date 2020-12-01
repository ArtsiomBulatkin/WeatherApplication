package com.example.weatherapplication.presenter

import android.content.Context
import com.example.weatherapplication.model.LocationModel
import com.example.weatherapplication.model.WeatherListModel
import com.example.weatherapplication.model.WeatherRepository
import com.example.weatherapplication.utils.SharedPreferenceManager
import com.example.weatherapplication.view.ViewContract
import com.example.weatherapplication.view.adapter.HeaderItem
import com.example.weatherapplication.view.adapter.Item
import com.example.weatherapplication.view.adapter.WeatherItem
import com.example.weatherapplication.view.adapter.WeatherListAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.header_item.view.*

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

        val items = mutableListOf<Item>()
        disposable.add(
            repo.getWeatherList(lat, lon)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ listWeatherModel -> view.loadListWeatherView(listWeatherModel) },
                    { throwable -> view.loadErrorMessage("CurrentList Error occurred: $throwable") })

        )
    }


    override fun dispose() {
        disposable.dispose()
    }

}