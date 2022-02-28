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
    @Query("SELECT * FROM user WHERE userId IN (:ids)")
    suspend fun getLocalUsers(ids: List<String>): List<UserEntity>

    @Query("SELECT * FROM user WHERE userId = :id")
    suspend fun getLocalUser(id: String): UserEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addLocalUser(user: UserEntity)
}