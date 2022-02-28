package com.bashkir.documentstasks.data.repositories.localdata.room

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.bashkir.documentstasks.data.models.PerformEntity
import com.bashkir.documentstasks.data.models.TaskEntity
import com.bashkir.documentstasks.data.models.TaskWithPerforms

@Dao
interface TaskDao {
    @Insert(onConflict = REPLACE)
    suspend fun insertAll(performs: List<PerformEntity>, vararg tasks: TaskEntity)

    @Transaction
    @Query("SELECT * FROM task")
    suspend fun getAllLocalTasks(): List<TaskWithPerforms>

    @Query("SELECT * FROM perform")
    suspend fun getAllPerforms(): List<PerformEntity>

    @Query("DELETE FROM task WHERE taskId NOT IN (:tasksId) ")
    suspend fun deleteAllNotIn(tasksId: List<Int>)

    @Query("DELETE FROM task")
    suspend fun deleteAll()

    @Query("DELETE FROM perform")
    suspend fun deleteAllPerforms()

    @Query("DELETE FROM perform WHERE performId NOT IN (:performsId) ")
    suspend fun deleteAllPerformsNotIn(performsId: List<Int>)
}