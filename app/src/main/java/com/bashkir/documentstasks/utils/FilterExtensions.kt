package com.bashkir.documentstasks.utils

import com.bashkir.documentstasks.data.models.Task
import com.bashkir.documentstasks.data.models.User

fun List<Task>.filter(searchText: String): List<Task> = filter { task ->
    if (searchText.isNotBlank() && searchText.isNotEmpty())
        task.title.contains(searchText) ||
                task.desc.contains(searchText)
    else true
}

fun Set<Task>.filter(searchText: String): List<Task> = this.toList().filter(searchText)

fun Map<Task, Boolean>.filter(searchText: String): Map<Task, Boolean> =
    keys.filter(searchText).associateWith { this[it]!! }

@JvmName("filterUser")
fun List<User>.filter(searchText: String): List<User> = filter { user ->
    if (searchText.isNotBlank() && searchText.isNotEmpty())
        user.email.contains(searchText) ||
                user.fullName.contains(searchText)
    else true
}