package com.bashkir.documentstasks.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

fun LocalDateTime.formatCutToString(): String =
    if (this.toLocalDate() == LocalDate.now())
        toLocalTime().formatToString() else toLocalDate().formatToString()

fun LocalDateTime.formatToString(): String =
    "${toLocalTime().formatToString()} ${toLocalDate().formatToString()}"

fun LocalDate.formatToString(): String =
    "${withZero(dayOfMonth)}.${withZero(monthValue)}.$year"

fun LocalTime.formatToString(): String =
    "${withZero(hour)}:${withZero(minute)}"

private fun withZero(num: Int): String =
    if (num.toString().count() == 1) "0$num" else num.toString()