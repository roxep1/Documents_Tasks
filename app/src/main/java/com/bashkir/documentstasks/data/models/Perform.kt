package com.bashkir.documentstasks.data.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bashkir.documentstasks.ui.navigation.Screen
import com.bashkir.documentstasks.utils.LocalDateTimeJsonAdapter
import com.google.gson.annotations.JsonAdapter
import java.time.LocalDateTime

data class Perform(
    val id: Int,
    val user: User,
    val taskId: Int,
    val status: PerformStatus,
    val comment: String? = null,

    @JsonAdapter(LocalDateTimeJsonAdapter::class)
    val statusChanged: LocalDateTime? = null,
    val documents: List<Document> = listOf()
) {
    val notificationId
        get() = "perform$id"

    fun toEntity() = PerformEntity(id, user.toEntity(), taskId, status, comment, statusChanged)
    fun toNotification(action: String): Notification = Notification(
        "Исполнение ${user.fullName}",
        user.toEntity(),
        action,
        statusChanged ?: LocalDateTime.now(),
        Screen.TaskDetail.destinationWithArgument(id.toString()),
        false,
        notificationId
    )
}

data class PerformForm(
    val user: UserForm
)

@Entity(tableName = "perform")
data class PerformEntity(
    @PrimaryKey val performId: Int,
    @Embedded val userPerformer: UserEntity,
    val taskPerformsId: Int,
    val status: PerformStatus,
    val comment: String?,
    val statusChanged: LocalDateTime?
) {
    fun toPerform(): Perform =
        Perform(performId, userPerformer.toUser(), taskPerformsId, status, comment, statusChanged)
}