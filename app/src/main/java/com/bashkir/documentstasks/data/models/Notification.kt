package com.bashkir.documentstasks.data.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import java.time.LocalDateTime

@Entity(tableName = "notification")
data class Notification(
    val title: String,
    @Embedded val author: UserEntity,
    val subject: String,
    val time: LocalDateTime,
    val destination: String,
    val checked: Boolean,
    @PrimaryKey val id: String
)