package com.example.weatherapplication.view.adapter

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import com.example.weatherapplication.R
import com.example.weatherapplication.model.WeatherListModel
import com.example.weatherapplication.utils.roundData
import com.example.weatherapplication.utils.timeConverter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.weather_item.view.*

class WeatherItem(val weatherModel: WeatherListModel.WeatherModel) : Item() {

    override val layoutId: Int = R.layout.weather_item

    override fun provideViewHolder(itemView: View): ViewHolder {
        return WeatherViewHolder(itemView)
    }

}

private class WeatherViewHolder(itemView: View) : ViewHolder(itemView) {

    private var timeTextView = itemView.timeTextView
    private var descriptionTextView = itemView.descriptionTextView
    private var tempTextView = itemView.tempTextView
    private var weatherImageView = itemView.weatherImageView

    @RequiresApi(Build.VERSION_CODES.O)
    override fun bind(item: Item) {
        val data = item as WeatherItem
        timeTextView.text = timeConverter(data.weatherModel.dateTime)
        descriptionTextView.text = data.weatherModel.weather[0].description
        val temp = roundData(data.weatherModel.mainTemp.temp)
        tempTextView.text = "$temp°С"
        Picasso.get()
            .load("https://openweathermap.org/img/wn/${data.weatherModel.weather[0].icon}@2x.png")
            .into(weatherImageView)
    }
}
