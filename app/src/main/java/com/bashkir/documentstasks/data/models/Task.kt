package com.bashkir.documentstasks.data.models

import com.bashkir.documentstasks.navigation.MainGraphs
import java.time.LocalDateTime

data class Task(
    val id: Int,
    override val title: String,
    val desc: String,
    val pubDate: LocalDateTime,
    val deadline: LocalDateTime,
    override val author: User,
    val performers: Map<User, Status>
) : Notifiable(
    title,
    author,
    "новое задание",
    pubDate,
    MainGraphs.MainGraph.TaskDetail.destinationWithArgument(id.toString())
)
