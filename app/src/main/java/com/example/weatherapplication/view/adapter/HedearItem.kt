package com.example.weatherapplication.view.adapter

import android.view.View
import com.example.weatherapplication.R
import kotlinx.android.synthetic.main.header_item.view.*

class HeaderItem(val text: String?) : Item() {

    override val layoutId: Int = R.layout.header_item

    override fun provideViewHolder(itemView: View): ViewHolder {
        return HeaderViewHolder(itemView)
    }

}

private class HeaderViewHolder(itemView: View) : ViewHolder(itemView) {

    private var dayOfWeekTextView = itemView.dayOfWeekTextView

    override fun bind(item: Item) {
        val data = item as HeaderItem
        dayOfWeekTextView.text = data.text
    }
}