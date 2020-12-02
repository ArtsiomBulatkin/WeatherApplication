package com.example.weatherapplication.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class Item {

    abstract val layoutId: Int

    abstract fun provideViewHolder(itemView: View): ViewHolder

    fun createViewHolder(parent: ViewGroup): ViewHolder {
        return provideViewHolder(
            LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        )
    }
}