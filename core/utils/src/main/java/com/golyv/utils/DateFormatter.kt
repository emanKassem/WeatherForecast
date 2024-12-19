package com.golyv.utils

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun Long.convertUnixToTime(): String {
    val instant = Instant.ofEpochSecond(this)

    val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())

    val formatter = DateTimeFormatter.ofPattern("K:mm a") // Example format: 6:30 AM

    return localDateTime.format(formatter)
}