package com.bashkir.documentstasks.data.repositories.localdata.room

import android.net.Uri
import androidx.room.TypeConverter
import java.time.LocalDateTime

class Converters {
    @TypeConverter
    fun fromLocalDateTime(value: LocalDateTime?): String? = value?.toString()

    @TypeConverter
    fun toLocalDateTime(value: String?): LocalDateTime? = value?.let{LocalDateTime.parse(it)}

    @TypeConverter
    fun fromUri(value: Uri?): String? = value?.toString()

    @TypeConverter
    fun toUri(value: String?): Uri? = value?.let{ Uri.parse(value) }
}