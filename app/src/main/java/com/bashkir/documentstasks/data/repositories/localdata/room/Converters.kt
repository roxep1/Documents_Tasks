package com.bashkir.documentstasks.data.repositories.localdata.room

import androidx.room.TypeConverter
import com.bashkir.documentstasks.data.models.Notifiable
import com.bashkir.documentstasks.data.models.Notification
import com.bashkir.documentstasks.data.models.PerformStatus
import java.time.LocalDateTime

class Converters {
    @TypeConverter
    fun fromLocalDateTime(value: LocalDateTime?): String? = value?.toString()

    @TypeConverter
    fun toLocalDateTime(value: String?): LocalDateTime? = value?.let{LocalDateTime.parse(it)}
}