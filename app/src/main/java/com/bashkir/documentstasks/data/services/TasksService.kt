package com.bashkir.documentstasks.data.services

import com.bashkir.documentstasks.data.models.*

class TasksService : SharedService() {

    suspend fun getAllTasks(): List<Task> = api.getAllTasks(preferences.getAuthorizedId())

    suspend fun addTask(task: TaskForm) =
        api.addTask(task.copy(author = UserForm(preferences.getAuthorizedId())))

    suspend fun inProgressTask(task: Task) = api.changePerformStatus(
        getMyPerform(task).id,
        PerformStatus.InProgress
    )

    suspend fun inProgressAllTasks(tasks: List<Task>) =
        api.inProgressAllPerforms(getMyPerforms(*tasks.toTypedArray()).map { it.id })

    suspend fun completeTask(task: Task) = api.changePerformStatus(
        getMyPerform(task).id,
        PerformStatus.Completed
    )

    suspend fun addDocumentToTask(task: Task, doc: DocumentForm) = api.addDocumentToPerform(
        getMyPerform(task).id,
        doc.copy(author = UserForm(preferences.getAuthorizedId()))
    )

    suspend fun addCommentToTask(task: Task, comment: String) = api.addCommentToPerform(
        getMyPerform(task).id,
        comment
    )

    fun onlyMyTasks(tasks: List<Task>): List<Task> {
        val id = preferences.getAuthorizedIdIfExist()
        return tasks.filter { it.performs.any { perform -> perform.user.id == id } }
    }

    fun onlyIssuedTasks(tasks: List<Task>): List<Task> {
        val id = preferences.getAuthorizedIdIfExist()
        return tasks.filter { it.author.id == id }
    }

    suspend fun deleteTask(task: Task) = api.deleteTask(task.id)
}