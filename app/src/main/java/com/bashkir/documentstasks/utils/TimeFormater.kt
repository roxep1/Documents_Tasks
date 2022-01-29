package com.bashkir.documentstasks.utils

import java.time.LocalDate
import java.time.LocalDateTime

fun LocalDateTime.formatToString(): String =
    if (this.toLocalDate() == LocalDate.now())
        "${withZero(hour)}:${withZero(minute)}:${withZero(second)}" else "${withZero(dayOfMonth)}.${withZero(monthValue)}.$year"

private fun withZero(num: Int): String =
    if (num.toString().count() == 1) "0$num" else num.toString()