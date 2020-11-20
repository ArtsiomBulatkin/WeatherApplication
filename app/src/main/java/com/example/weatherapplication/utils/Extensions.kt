package com.example.weatherapplication.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@RequiresApi(Build.VERSION_CODES.O)
fun timeConverter(dateTime: String): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val time = LocalDateTime.parse(dateTime, formatter)
    return "${time.hour}:00"
}

fun roundTemp(temp: String): String {
    val tempRound = temp.toDouble().roundToInt()
    return tempRound.toString()
}