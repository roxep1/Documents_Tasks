package com.bashkir.documentstasks.utils

import com.bashkir.documentstasks.data.models.Task
import com.bashkir.documentstasks.data.models.User

fun List<Task>.filter(searchText: String): List<Task> = filter { task ->
    searchText.lowercase().let {
        if (it.isNotBlank() && it.isNotEmpty())
            task.title.lowercase().contains(it) ||
                    task.desc.lowercase().contains(it)
        else true
    }
}

fun Set<Task>.filter(searchText: String): List<Task> = this.toList().filter(searchText)

fun Map<Task, Boolean>.filter(searchText: String): Map<Task, Boolean> =
    keys.filter(searchText).associateWith { this[it]!! }

@JvmName("filterUser")
fun List<User>.filter(searchText: String): List<User> = filter { user ->
    searchText.lowercase().let {
        if (it.isNotBlank() && it.isNotEmpty())
            user.email.lowercase().contains(it) ||
                    user.fullName.lowercase().contains(it)
        else true
    }
}