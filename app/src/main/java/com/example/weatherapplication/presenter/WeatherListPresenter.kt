package com.example.weatherapplication.presenter

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weatherapplication.model.LocationModel
import com.example.weatherapplication.model.WeatherListModel
import com.example.weatherapplication.model.WeatherRepository
import com.example.weatherapplication.utils.SharedPreferenceManager
import com.example.weatherapplication.utils.dateToDayOfWeekly
import com.example.weatherapplication.view.ViewContract
import com.example.weatherapplication.view.adapter.HeaderItem
import com.example.weatherapplication.view.adapter.Item
import com.example.weatherapplication.view.adapter.WeatherItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class WeatherListPresenter(private val view: ViewContract.WeatherListView) :
    PresenterContract.WeatherListContract {

    private val repo = WeatherRepository()
    private val disposable = CompositeDisposable()
    private lateinit var prefs: SharedPreferenceManager
    private lateinit var locationModel: LocationModel
    private var list: MutableList<Item> = mutableListOf()

    override fun loadLocation(context: Context) {
        prefs = SharedPreferenceManager()
        locationModel = prefs.getLocation(context)
        view.loadLocation(locationModel.latitude, locationModel.longitude)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun loadDataListWeather(lat: String, lon: String) {

        disposable.add(
            repo.getWeatherList(lat, lon)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ listWeatherModel ->
                    view.loadListWeatherView(
                        sorted(listWeatherModel),
                        listWeatherModel.city.city
                    )
                },
                    { throwable -> view.loadErrorMessage("CurrentList Error occurred: $throwable") })
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun sorted(weatherListModel: WeatherListModel): List<Item> {
        var day = ""
        weatherListModel.list.forEach {
            if (day != dateToDayOfWeekly(it.dateTime)) {
                day = dateToDayOfWeekly(it.dateTime)
                if (list.size == 0) {
                    list.add(HeaderItem("TODAY"))
                } else {
                    list.add(HeaderItem(day))
                }
            }
            list.add(WeatherItem(it))
        }
        return list
    }

    override fun dispose() {
        disposable.dispose()
    }
}