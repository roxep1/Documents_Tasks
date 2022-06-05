package com.bashkir.documentstasks.data.services

import com.bashkir.documentstasks.data.models.*
import com.bashkir.documentstasks.data.repositories.localdata.room.TaskDao
import com.bashkir.documentstasks.utils.getAllPerforms
import org.koin.java.KoinJavaComponent.inject

class TasksService : TasksNotificationService() {
    private val taskDao: TaskDao by inject(TaskDao::class.java)

    suspend fun getAllTasks(): List<Task> =
        if (isOnline)
            api.getAllTasks().let { tasks ->
                doLocalWork(tasks)
                tasks
            }
        else taskDao.getAllLocalTasks().map { it.toTask() }

    private suspend fun doLocalWork(tasks: List<Task>) =
        taskDao.run {
            notificationsWithTasks(tasks)
            insertAll(
                tasks.getAllPerforms().map { it.toEntity() },
                *tasks.map { it.toEntity() }.toTypedArray()
            )
            deleteAllPerformsNotIn(tasks.getAllPerforms().map { it.id })
            deleteAllNotIn(tasks.map { it.id })
        }


    suspend fun addTask(task: TaskForm, files: List<FileForm>) {
        val user = UserForm(preferences.authorizedId)
        api.addTask(
            TaskWithFiles(
                task.copy(
                    author = user,
                    documents = task.documents.map { it.copy(author = user) }),
                files
            )
        )
    }

    suspend fun inProgressTask(task: Task) = api.changePerformStatus(
        getMyPerform(task).id,
        PerformStatus.InProgress
    )

    suspend fun completeTask(task: Task) = api.changePerformStatus(
        getMyPerform(task).id,
        PerformStatus.Completed
    )

    suspend fun addDocumentToTask(task: Task, doc: DocumentForm, file: FileForm) =
        api.addDocumentToPerform(
            getMyPerform(task).id,
            DocumentWithFile(doc.copy(author = UserForm(preferences.authorizedId)), file)
        )

    suspend fun addCommentToTask(task: Task, comment: String) = api.addCommentToPerform(
        getMyPerform(task).id,
        comment
    )

    suspend fun deleteTask(task: Task) = api.deleteTask(task.id)

    fun getMyId(): String = preferences.authorizedId
}