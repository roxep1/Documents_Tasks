package com.bashkir.documentstasks.utils

import java.time.LocalDate
import java.time.LocalDateTime

fun LocalDateTime.formatToString(): String =
    if (this.toLocalDate() == LocalDate.now()) "$hour:$minute:$second" else "$dayOfMonth.$monthValue.$year"