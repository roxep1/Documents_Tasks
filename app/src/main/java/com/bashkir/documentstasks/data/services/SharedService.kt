package com.bashkir.documentstasks.data.services

import com.bashkir.documentstasks.data.models.*
import com.bashkir.documentstasks.data.repositories.DocumentsTasksApi
import com.bashkir.documentstasks.data.repositories.localdata.preferences.LocalUserPreferences
import com.bashkir.documentstasks.data.repositories.localdata.room.UserDao
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.get
import org.koin.java.KoinJavaComponent.inject

open class SharedService {
    protected val api: DocumentsTasksApi by inject(DocumentsTasksApi::class.java)
    protected val preferences: LocalUserPreferences by inject(LocalUserPreferences::class.java)
    protected val userDao: UserDao by inject(UserDao::class.java)
    protected val isOnline: Boolean
        get() = get(Boolean::class.java, named("isOnline"))

    suspend fun getAllUsers(): List<User> {
        val id = preferences.getAuthorizedIdIfExist()
        return api.getUsers().filter { it.id != id }
    }

    private inline fun <T> withAuthorizedId(action: (id: String) -> T): T =
        action(preferences.authorizedId)

    private fun getMyPerforms(vararg tasks: Task): List<Perform> =
        withAuthorizedId { id -> tasks.map { task -> task.performs.first { it.user.id == id } } }

    protected fun getTasksToDo(tasks: List<Task>): List<Task> =
        withAuthorizedId { id -> tasks.filter { it.author.id != id } }

    fun getMyPerform(task: Task): Perform = getMyPerforms(task).first()

    suspend fun getFile(document: Document): File = api.getFile(document.id)

    fun isMyId(userId: String): Boolean = preferences.authorizedId == userId
}