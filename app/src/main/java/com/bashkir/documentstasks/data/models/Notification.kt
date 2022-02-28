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
    @PrimaryKey(autoGenerate = true) val id: Int
) : Notifiable(
    title,
    author.toUser(),
    subject,
    time,
    destination,
    checked,
    id
)


abstract class Notifiable(
    @Ignore @Expose val titleOfAction: String,
    @Ignore @Expose val notifier: User,
    @Ignore @Expose val action: String,
    @Ignore @Expose val timeOfAction: LocalDateTime,
    @Ignore @Expose val destinationOfAction: String,
    @Ignore @Expose val checkedStatus: Boolean = false,
    @Ignore @Expose val notifyId: Int = 0
) {
    fun toNotification(): Notification = Notification(
        titleOfAction,
        notifier.toEntity(),
        action,
        timeOfAction,
        destinationOfAction,
        checkedStatus,
        notifyId
    )
}