package com.bashkir.documentstasks.data.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.bashkir.documentstasks.ui.navigation.Screen
import com.bashkir.documentstasks.utils.LocalDateTimeJsonAdapter
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class Task(
    val id: Int,
    val title: String,
    val desc: String,

    @JsonAdapter(LocalDateTimeJsonAdapter::class)
    val deadline: LocalDateTime,
    val author: User,

    @JsonAdapter(LocalDateTimeJsonAdapter::class)
    @SerializedName("created")
    val pubDate: LocalDateTime,
    val performs: List<Perform> = listOf(),
    val documents: List<Document> = listOf()
) {
    val notificationId
        get() = "task$id"

    fun toEntity(): TaskEntity = TaskEntity(id, title, desc, deadline, author.toEntity(), pubDate)
    fun toNotification(action: String, time: LocalDateTime = pubDate): Notification =
        Notification(
            title,
            author.toEntity(),
            action,
            time,
            Screen.TaskDetail.destinationWithArgument(id.toString()),
            false,
            notificationId
        )
}

data class TaskForm(
    val title: String,
    val desc: String,

    @JsonAdapter(LocalDateTimeJsonAdapter::class)
    val deadline: LocalDateTime,
    val performs: List<PerformForm>,
    val documents: List<DocumentForm>,
    val author: UserForm? = null
)

@Entity(tableName = "task")
data class TaskEntity(
    @PrimaryKey val taskId: Int,
    val title: String,
    val desc: String,
    val deadline: LocalDateTime,
    @Embedded val author: UserEntity,
    val pubDate: LocalDateTime
)

data class FullLocalTask(
    @Embedded val task: TaskEntity,
    @Relation(
        parentColumn = "taskId",
        entityColumn = "taskPerformsId"
    )
    val performs: List<PerformEntity>,
    @Relation(
        parentColumn = "taskId",
        entityColumn = "taskId"
    )
    val documents: List<DocumentEntity>
) {
    fun toTask(): Task = task.run {
        Task(
            taskId,
            title,
            desc,
            deadline,
            author.toUser(),
            pubDate,
            performs.map { it.toPerform() },
            documents.map { it.toDocument() }
        )
    }
}