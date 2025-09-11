package com.simbiri.myquiz.data.util

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime


@OptIn(ExperimentalTime::class)
fun Long.toFormattedDateTime(): String{
    val instant = Instant.fromEpochMilliseconds(this)
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    return "${localDateTime.date}${localDateTime.time.toString().substring(0,8)}"

}