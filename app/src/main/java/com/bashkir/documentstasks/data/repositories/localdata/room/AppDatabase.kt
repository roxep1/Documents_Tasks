package com.bashkir.documentstasks.data.repositories.localdata.room

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.bashkir.documentstasks.data.models.User
import com.bashkir.documentstasks.data.models.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 2,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}