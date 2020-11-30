package com.example.weatherapplication.view

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.weatherapplication.R
import com.example.weatherapplication.model.WeatherListModel
import com.example.weatherapplication.utils.dateToDayOfWeekly
import com.example.weatherapplication.utils.roundData
import com.example.weatherapplication.utils.timeConverter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.header_item.view.*
import kotlinx.android.synthetic.main.weather_item.view.*

class AdapterWeatherList : RecyclerView.Adapter<ViewHolder>() {

    private var weatherList = emptyList<WeatherListModel.WeatherModel>()
    private var viewHolder: ViewHolder? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        return if (viewType == ITEM) {
            val view = inflate.inflate(R.layout.header_item, parent, false)
            viewHolder = HeaderViewHolder(view)
            viewHolder as HeaderViewHolder
        } else {
            val view = inflate.inflate(R.layout.weather_item, parent, false)
            viewHolder = ItemViewHolder(view)
            viewHolder as ItemViewHolder
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> {
                viewHolder = holder
                val model = weatherList[position]
                (viewHolder as HeaderViewHolder).bind(model)
            }
            else -> {
                viewHolder = holder
                val model = weatherList[position]
                (viewHolder as ItemViewHolder).bind(model)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (weatherList[position]) {
            is WeatherListModel.WeatherModel -> HEADER_ITEM
            else -> ITEM
        }
    }

    fun update(data: List<WeatherListModel.WeatherModel>) {
        weatherList = data
        notifyDataSetChanged()
    }

    class HeaderViewHolder(itemView: View) : ViewHolder(itemView) {

        private var dayOfWeekTextView = itemView.dayOfWeekTextView

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(weatherListModel: WeatherListModel.WeatherModel) {
            dayOfWeekTextView.text = dateToDayOfWeekly(weatherListModel.dateTime)
        }
    }

    class ItemViewHolder(itemView: View) : ViewHolder(itemView) {

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

    override fun getItemCount(): Int {
        return weatherList.size
    }

    companion object {
        private const val HEADER_ITEM = 0
        private const val ITEM = 1
    }

}