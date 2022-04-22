package com.bashkir.documentstasks.data.services

import com.bashkir.documentstasks.data.models.Notification
import com.bashkir.documentstasks.data.models.Task
import com.bashkir.documentstasks.data.repositories.localdata.room.NotificationDao
import com.bashkir.documentstasks.data.repositories.localdata.room.TaskDao
import com.bashkir.documentstasks.utils.getAllPerforms
import kotlinx.coroutines.flow.Flow
import org.koin.java.KoinJavaComponent
import java.time.LocalDateTime

open class NotificationsService : SharedService() {
    private val notificationDao: NotificationDao by KoinJavaComponent.inject(NotificationDao::class.java)

    suspend fun deleteNotification(notification: Notification) =
        notificationDao.deleteNotification(notification)

    fun loadAllNotifications(): Flow<List<Notification>> = notificationDao.loadAll()

    protected suspend fun TaskDao.notificationsWithTasks(tasks: List<Task>) {
        notifyAboutDeleted(tasks)
        deleteNotUpToDate(tasks)
        notifyAboutNew(tasks)
        notifyAboutStatusChange(tasks)
    }

    private suspend fun deleteNotUpToDate(upToDateTasks: List<Task>) =
        upToDateTasks.getAllPerforms().let { performs ->
            notificationDao.deleteAllNotIn(
                performs.map { it.notificationId } + upToDateTasks.map { it.notificationId }
            )
        }

    private suspend fun TaskDao.notifyAboutNew(tasks: List<Task>) =
        getToDoTasks(preferences.authorizedId).map { it.toTask() }.let { oldTasksToDo ->
            getTasksToDo(tasks)
                .filter { !oldTasksToDo.contains(it) }
                .let {
                    notificationDao.insertAll(
                        *it.map { task ->
                            task.toNotification("добавил новое задание \"${task.title}\"")
                        }.toTypedArray()
                    )
                }
        }

    private suspend fun TaskDao.notifyAboutStatusChange(tasks: List<Task>) =
        getIssuedTasks(preferences.authorizedId).map { it.toTask() }.getAllPerforms()
            .forEach { perform ->
                tasks.getAllPerforms()
                    .find { it.id == perform.id }?.run {
                        if (status != perform.status
                        )
                            notificationDao.insertAll(
                                perform.toNotification("обновил статус выполнения задачи на ${perform.status.text}")
                            )
                    }
            }

    private suspend fun TaskDao.notifyAboutDeleted(tasks: List<Task>) =
        getToDoTasks(preferences.authorizedId).map { it.toTask() }
            .filter { task -> !tasks.map { it.id }.contains(task.id) }
            .let { deletedTasks ->
                notificationDao
                    .insertAll(
                        *deletedTasks
                            .map {
                                it.toNotification(
                                    "удалил задание \"${it.title}\"",
                                    LocalDateTime.now()
                                )
                            }
                            .toTypedArray()
                    )
            }

}