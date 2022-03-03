package com.bashkir.documentstasks.data.services

import com.bashkir.documentstasks.data.models.Perform
import com.bashkir.documentstasks.data.models.Task
import com.bashkir.documentstasks.data.models.User
import com.bashkir.documentstasks.data.repositories.DocumentsTasksApi
import com.bashkir.documentstasks.data.repositories.localdata.preferences.LocalUserPreferences
import com.bashkir.documentstasks.data.repositories.localdata.room.NotificationDao
import com.bashkir.documentstasks.data.repositories.localdata.room.UserDao
import org.koin.java.KoinJavaComponent
import org.koin.java.KoinJavaComponent.inject

open class SharedService {
    protected val api: DocumentsTasksApi by inject(DocumentsTasksApi::class.java)
    protected val preferences: LocalUserPreferences by inject(LocalUserPreferences::class.java)
    protected val userDao: UserDao by inject(UserDao::class.java)

    suspend fun getAllUsers(): List<User> {
        val id = preferences.getAuthorizedIdIfExist()
        return api.getUsers().filter { it.id != id }
    }

    private fun <T> withAuthorizedId(action: (id: String) -> T): T = action(preferences.getAuthorizedId())

    protected fun getMyPerforms(vararg tasks: Task): List<Perform> =
        withAuthorizedId { id -> tasks.map {task -> task.performs.first { it.user.id == id } } }

    protected fun getTasksToDo(vararg tasks: Task): List<Task> =
        withAuthorizedId { id -> tasks.filter { it.author.id != id } }

    fun getMyPerform(task: Task): Perform = getMyPerforms(task).first()
}