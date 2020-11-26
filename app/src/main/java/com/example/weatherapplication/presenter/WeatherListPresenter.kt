package com.example.weatherapplication.presenter

import com.example.weatherapplication.model.WeatherRepository
import com.example.weatherapplication.view.ViewContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class WeatherListPresenter (private val view: ViewContract.WeatherListView)
    : PresenterContract.WeatherListContract {

    private val repo = WeatherRepository()
    private val disposable = CompositeDisposable()

    override fun loadDataListWeather(lat: String, lon: String) {
        disposable.add(
            repo.getWeatherList(lat, lon)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ listWeather -> view.loadListWeatherView(listWeather) },
                    { throwable -> view.loadErrorMessage("CurrentList Error occurred: $throwable") })

        )
    }
    override fun dispose() {
        disposable.dispose()
    }
}