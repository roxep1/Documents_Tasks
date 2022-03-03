package com.bashkir.documentstasks.data.services

import com.bashkir.documentstasks.data.models.Notification
import com.bashkir.documentstasks.data.models.Task
import com.bashkir.documentstasks.data.repositories.localdata.room.NotificationDao
import com.bashkir.documentstasks.data.repositories.localdata.room.TaskDao
import com.bashkir.documentstasks.utils.getAllPerforms
import kotlinx.coroutines.flow.Flow
import org.koin.java.KoinJavaComponent

open class NotificationsService : SharedService() {
    private val notificationDao: NotificationDao by KoinJavaComponent.inject(NotificationDao::class.java)

    protected suspend fun TaskDao.notificationsWithTasks(tasks: List<Task>) {
        deleteNotUpToDate(tasks)
        notifyAboutNew(tasks)
        notifyAboutStatusChange(tasks)
    }

    private suspend fun TaskDao.deleteNotUpToDate(upToDateTasks: List<Task>) =
        upToDateTasks.getAllPerforms().let { performs ->
            deleteAllPerformsNotIn(performs.map { it.id })
            deleteAllNotIn(upToDateTasks.map { it.id })
            notificationDao.deleteAllNotIn(
                performs.map { it.notificationId } + upToDateTasks.map { it.notificationId }
            )
        }

    private suspend fun TaskDao.notifyAboutNew(tasks: List<Task>) =
        getTasksToDo(*getAllLocalTasks().map { it.toTask() }.toTypedArray()).let { oldTasksToDo ->
            getTasksToDo(*tasks.toTypedArray())
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
        getAllPerforms().map { it.toPerform() }.forEach { perform ->
            if (tasks.getAllPerforms()
                    .find { it.id == perform.id }?.status != perform.status
            )
                notificationDao.insertAll(
                    perform.toNotification("обновил статус выполнения задачи на ${perform.status.text}")
                )
        }

    fun loadAllNotifications(): Flow<List<Notification>> = notificationDao.loadAll()
}