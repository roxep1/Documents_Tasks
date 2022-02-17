package com.bashkir.documentstasks.data.services

import com.bashkir.documentstasks.data.models.Perform
import com.bashkir.documentstasks.data.models.Task
import com.bashkir.documentstasks.data.models.User
import com.bashkir.documentstasks.data.repositories.DocumentsTasksApi
import com.bashkir.documentstasks.data.repositories.localdata.preferences.LocalUserPreferences
import org.koin.java.KoinJavaComponent.inject

open class SharedService {
    protected val api: DocumentsTasksApi by inject(DocumentsTasksApi::class.java)
    protected val preferences: LocalUserPreferences by inject(LocalUserPreferences::class.java)

    suspend fun getAllUsers(): List<User> {
        val id = preferences.getAuthorizedIdIfExist()
        return api.getUsers().filter { it.id != id }
    }

    fun <T> withAuthorizedId(action: (id: String) -> T): T = action(preferences.getAuthorizedId())

    fun getMyPerform(task: Task): Perform = withAuthorizedId {id ->  task.performs.first { it.user.id == id }}
}