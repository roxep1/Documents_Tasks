package com.bashkir.documentstasks.data.services

import com.bashkir.documentstasks.data.models.Notification
import com.bashkir.documentstasks.data.models.Task
import com.bashkir.documentstasks.data.repositories.localdata.room.NotificationDao
import com.bashkir.documentstasks.data.repositories.localdata.room.TaskDao
import com.bashkir.documentstasks.utils.getAllPerforms
import kotlinx.coroutines.flow.Flow
import org.koin.java.KoinJavaComponent

open class NotificationsService: SharedService() {
    protected val notificationDao: NotificationDao by KoinJavaComponent.inject(NotificationDao::class.java)

    private suspend fun TaskDao.deleteNotUpToDate(upToDateTasks: List<Task> ){
        deleteAllPerformsNotIn(upToDateTasks.getAllPerforms().map { it.id })
        deleteAllNotIn(upToDateTasks.map { it.id })
    }

    fun loadAllNotifications(): Flow<List<Notification>> = notificationDao.loadAll()
}