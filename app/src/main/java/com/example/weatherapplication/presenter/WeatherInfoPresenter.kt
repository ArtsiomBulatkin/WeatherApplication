package com.example.weatherapplication.presenter

import com.example.weatherapplication.model.WeatherRepository
import com.example.weatherapplication.view.ViewContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver

import io.reactivex.schedulers.Schedulers


class WeatherInfoPresenter(val view: ViewContract) : PresenterContract {

    private val repo = WeatherRepository()
    private val disposable = CompositeDisposable()


    override fun loadDataCurrentWeather(lat: String, lon: String) {
        disposable.add(
            repo.getWeather(lat, lon)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { currentWeather -> view.loadCurrentWeatherView(currentWeather) },
                    { throwable -> view.loadErrorMessage("CurrentWeather Error occurred: $throwable") }
                )

        )
    }

    override fun loadDataListWeather(lat: String, lon: String) {
        disposable.add(
            repo.getWeatherList(lat, lon)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { listWeather -> view.loadListWeatherView(listWeather) },
                    { throwable -> view.loadErrorMessage("CurrentWeather Error occurred: $throwable") }
                )
        )
    }

    override fun dispose() {
        disposable.dispose()
    }


}