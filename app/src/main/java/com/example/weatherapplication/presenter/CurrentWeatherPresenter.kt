package com.example.weatherapplication.presenter

import com.example.weatherapplication.model.WeatherRepository
import com.example.weatherapplication.view.ViewContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CurrentWeatherPresenter(private val view: ViewContract.CurrentWeatherView) :
    PresenterContract.CurrentWeatherContract {

    private val repo = WeatherRepository()
    private val disposable = CompositeDisposable()

    override fun loadDataCurrentWeather(lat: String, lon: String) {
        disposable.add(
            repo.getWeather(lat, lon)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ currentWeather -> view.loadCurrentWeatherView(currentWeather) },
                    { throwable -> view.loadErrorMessage("CurrentWeather Error occurred: $throwable") })
        )
    }

    fun onShareText() {
        view.shareText()
    }

    override fun dispose() {
        disposable.dispose()
    }
}