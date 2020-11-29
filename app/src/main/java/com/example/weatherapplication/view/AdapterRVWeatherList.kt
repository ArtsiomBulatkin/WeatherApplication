package com.example.weatherapplication.view

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView

import com.example.weatherapplication.R
import com.example.weatherapplication.model.WeatherListModel
import com.example.weatherapplication.utils.roundData
import com.example.weatherapplication.utils.timeConverter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.weather_item.view.*


class AdapterRVWeatherList : RecyclerView.Adapter<AdapterRVWeatherList.WViewHolder>() {

    private var weatherList = emptyList<WeatherListModel.WeatherModel>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterRVWeatherList.WViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.weather_item, parent, false)
        return WViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: AdapterRVWeatherList.WViewHolder, position: Int) {
        holder.bind(weatherList[position])
    }

    override fun getItemCount(): Int {
        return weatherList.size
    }

    fun update(data: List<WeatherListModel.WeatherModel>) {
        weatherList = data
        notifyDataSetChanged()
    }

    class WViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var timeTextView = itemView.timeTextView
        private var descriptionTextView = itemView.descriptionTextView
        private var tempTextView = itemView.tempTextView
        private var weatherImageView = itemView.weatherImageView

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(weatherListModel: WeatherListModel.WeatherModel) {
            timeTextView.text = timeConverter(weatherListModel.dateTime)
            descriptionTextView.text = weatherListModel.weather[0].description
            val temp = roundData(weatherListModel.mainTemp.temp)
            tempTextView.text = "$temp°С"
            Picasso.get()
                .load("https://openweathermap.org/img/wn/${weatherListModel.weather[0].icon}@2x.png")
                .into(weatherImageView)
        }
    }
}