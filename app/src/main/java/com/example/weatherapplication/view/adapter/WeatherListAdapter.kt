package com.example.weatherapplication.view.adapter

import android.content.Context

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class WeatherListAdapter(context: Context) : RecyclerView.Adapter<ViewHolder>() {


    private var items: List<Item> = emptyList()

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = items[position].layoutId

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item = items.first { it.layoutId == viewType }
        return item.createViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }


    fun setItem(items: List<Item>) {
        this.items = items
        notifyDataSetChanged()
    }



}
