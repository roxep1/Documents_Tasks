package com.bashkir.documentstasks.utils

import com.bashkir.documentstasks.data.models.Documentable
import com.bashkir.documentstasks.data.models.Task
import com.bashkir.documentstasks.data.models.User
import com.bashkir.documentstasks.ui.components.filters.TaskFilterOption

fun List<Task>.filter(searchText: String): List<Task> = filter { task ->
    searchText.lowercase().let {
        if (it.isNotBlank() && it.isNotEmpty())
            task.title.lowercase().contains(it) ||
                    task.desc.lowercase().contains(it)
        else true
    }
}

fun Set<Task>.filter(searchText: String): List<Task> = this.toList().filter(searchText)

@JvmName("filterUser")
fun List<User>.filter(searchText: String): List<User> = filter { user ->
    searchText.lowercase().let {
        if (it.isNotBlank() && it.isNotEmpty())
            user.email.lowercase().contains(it) ||
                    user.fullName.lowercase().contains(it)
        else true
    }
}

@JvmName("filterDocumentable")
fun List<Documentable>.filter(text: String): List<Documentable> = filter {
    it.toDocument().run {
        author.fullName.contains(text) ||
                title.contains(text) ||
                desc?.contains(text)?:false ||
                templateId?.contains(text)?:false
    }
}