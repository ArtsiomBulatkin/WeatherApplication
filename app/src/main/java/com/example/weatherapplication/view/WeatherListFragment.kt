package com.example.weatherapplication.view

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapplication.R
import com.example.weatherapplication.presenter.WeatherListPresenter
import com.example.weatherapplication.view.adapter.Item
import com.example.weatherapplication.view.adapter.WeatherListAdapter
import kotlinx.android.synthetic.main.fragment_weather_list.*
import timber.log.Timber

class WeatherListFragment : Fragment(), ViewContract.WeatherListView {

    private lateinit var presenter: WeatherListPresenter
    private lateinit var adapter: WeatherListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_weather_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = WeatherListAdapter(requireContext())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        presenter = WeatherListPresenter(this)
        presenter.loadLocation(requireContext())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun loadLocation(lat: String?, lon: String?) {
        presenter.loadDataListWeather(lat!!, lon!!)
    }

    override fun loadListWeatherView(list: List<Item>) {
        adapter.setItem(list)
    }

    override fun loadErrorMessage(message: String) {
        Timber.e(message)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dispose()
    }

}