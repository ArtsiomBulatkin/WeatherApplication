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

@RequiresApi(Build.VERSION_CODES.O)
fun dateToDayOfWeekly(dateTime: String): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val time = LocalDateTime.parse(dateTime, formatter)
    return time.dayOfWeek.toString()
}


fun roundData(data: String): String {
    val tempRound = data.toDouble().roundToInt()
    return tempRound.toString()
}

fun meterToKm(int: Int): Int {
    return int / 1000
}

fun windToDescription(deg: Int): String {
    if (deg in 360..21) return "N"
    if (deg in 22..44) return "NNE"
    if (deg in 45..66) return "NE"
    if (deg in 67..89) return "ENE"
    if (deg in 90..111) return "E"
    if (deg in 112..134) return "ESE"
    if (deg in 135..156) return "SE"
    if (deg in 157..179) return "SSE"
    if (deg in 180..201) return "S"
    if (deg in 202..224) return "SSW"
    if (deg in 225..246) return "SW"
    if (deg in 247..269) return "WSW"
    if (deg in 270..291) return "W"
    if (deg in 292..314) return "WNW"
    if (deg in 315..336) return "NW"
    if (deg in 337..359) return "NNW"

    return "no data"
}



