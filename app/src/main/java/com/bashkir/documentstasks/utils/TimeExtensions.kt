package com.bashkir.documentstasks.utils

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
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
    if (num.toString().length == 1) "0$num" else num.toString()

class LocalDateTimeJsonAdapter : TypeAdapter<LocalDateTime>() {
    override fun write(out: JsonWriter, value: LocalDateTime?) {
        out.value(value.toString())
    }

    override fun read(`in`: JsonReader): LocalDateTime = LocalDateTime.parse(`in`.nextString())
}