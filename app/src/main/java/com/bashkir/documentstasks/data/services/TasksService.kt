package com.bashkir.documentstasks.data.services

import com.bashkir.documentstasks.data.models.*
import com.bashkir.documentstasks.data.repositories.localdata.room.TaskDao
import com.bashkir.documentstasks.utils.getAllPerforms
import org.koin.java.KoinJavaComponent.inject

class TasksService : NotificationsService() {
    private val taskDao: TaskDao by inject(TaskDao::class.java)

    suspend fun getAllTasks(): List<Task> {
        api.getAllTasks(preferences.getAuthorizedId()).let { tasks ->
            taskDao.run {
                getAllLocalTasks().run {
                    map { it.toTask() }.forEach {
                        if (!tasks.contains(it))
                            notificationDao.insertAll(it.toNotification())
                    }
                }
                getAllPerforms().map { it.toPerform() }.forEach { perform ->
                    if (tasks.getAllPerforms()
                            .find { it.id == perform.id }?.status != perform.status
                    )
                        notificationDao.insertAll(perform.toNotification())
                }
                insertAll(
                    tasks.getAllPerforms().map { it.toEntity() },
                    *tasks.map { it.toEntity() }.toTypedArray()
                )
            }
            return tasks
        }
    }

    suspend fun addTask(task: TaskForm) =
        api.addTask(task.copy(author = UserForm(preferences.getAuthorizedId())))

    suspend fun inProgressTask(task: Task) = api.changePerformStatus(
        getMyPerform(task).id,
        PerformStatus.InProgress
    )

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

    fun onlyIssuedTasks(tasks: List<Task>): List<Task> {
        val id = preferences.getAuthorizedIdIfExist()
        return tasks.filter { it.author.id == id }
    }

    suspend fun deleteTask(task: Task) = api.deleteTask(task.id)
}