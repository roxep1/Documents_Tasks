package com.bashkir.documentstasks.data.repositories.localdata.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.bashkir.documentstasks.data.models.Notification
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {
    @Insert(onConflict = REPLACE)
    suspend fun insertAll(vararg notifications: Notification)

    @Query("SELECT * FROM notification")
    fun loadAll(): Flow<List<Notification>>

    @Query("DELETE FROM notification WHERE id NOT IN (:ids)")
    suspend fun deleteAllNotIn(ids: List<String>)

    @Delete
    suspend fun deleteNotification(notification: Notification)
}