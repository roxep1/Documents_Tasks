package com.bashkir.documentstasks.data.repositories.localdata.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bashkir.documentstasks.data.models.User
import com.bashkir.documentstasks.data.models.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM user WHERE id IN (:ids)")
    fun loadLocalUsers(ids: List<String>): Flow<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addLocalUser(user: UserEntity)
}