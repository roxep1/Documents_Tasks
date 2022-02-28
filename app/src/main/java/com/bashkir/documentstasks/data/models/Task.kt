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
    val performs: List<Perform> = listOf()
) : Notifiable(
    title,
    author,
    "добавил новое задание для вас",
    pubDate,
    Screen.TaskDetail.destinationWithArgument(id.toString()),
    notifyId = id
) {
    fun toEntity(): TaskEntity = TaskEntity(id, title, desc, deadline, author.toEntity(), pubDate)
}

data class TaskForm(
    val title: String,
    val desc: String,

    @JsonAdapter(LocalDateTimeJsonAdapter::class)
    val deadline: LocalDateTime,
    val performs: List<PerformForm>,
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

data class TaskWithPerforms(
    @Embedded val task: TaskEntity,
    @Relation(
        parentColumn = "taskId",
        entityColumn = "taskPerformsId"
    )
    val performs: List<PerformEntity>
) {
    fun toTask(): Task = task.run {
        Task(
            taskId,
            title,
            desc,
            deadline,
            author.toUser(),
            pubDate,
            performs.map { it.toPerform() })
    }
}