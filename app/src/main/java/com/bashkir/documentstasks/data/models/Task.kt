package com.bashkir.documentstasks.data.models

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
)
//    : Notifiable(
//    title,
//    author,
//    "новое задание",
//    pubDate,
//    MainNavGraphs.Screen.TaskDetail.destinationWithArgument(id.toString())
//)

data class TaskForm(
    val title: String,
    val desc: String,

    @JsonAdapter(LocalDateTimeJsonAdapter::class)
    val deadline : LocalDateTime,
    val performs: List<PerformForm>,
    val author: UserForm? = null
)