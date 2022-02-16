package com.bashkir.documentstasks.data.services

import com.bashkir.documentstasks.data.models.*

class TasksService: SharedService() {

    suspend fun getAllTasks(): List<Task> = api.getAllTasks(preferences.getAuthorizedId())

    suspend fun addTask(task: TaskForm) =
        api.addTask(task.copy(author = UserForm(preferences.getAuthorizedId())))

    suspend fun inProgressPerform(performId: Int) = api.changePerformStatus(
        performId,
        PerformStatus.InProgress
    )

    suspend fun completePerform(performId: Int) = api.changePerformStatus(
        performId,
        PerformStatus.Completed
    )

    suspend fun addDocumentToPerform(performId: Int, doc: DocumentForm) = api.addDocumentToPerform(
        performId,
        doc.copy(author = UserForm(preferences.getAuthorizedId()))
    )

    suspend fun addCommentToPerform(performId: Int, comment: String) = api.addCommentToPerform(
        performId,
        comment
    )

    fun onlyMyTasks(tasks: List<Task>): List<Task> {
        val id = preferences.getAuthorizedIdIfExist()
        return tasks.filter { it.performs.any {perform -> perform.user.id == id }}
    }

    fun onlyIssuedTasks(tasks: List<Task>): List<Task> {
        val id = preferences.getAuthorizedIdIfExist()
        return tasks.filter { it.author.id == id }
    }
}