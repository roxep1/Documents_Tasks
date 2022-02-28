package com.bashkir.documentstasks.data.repositories.localdata.room

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.AutoMigrationSpec
import com.bashkir.documentstasks.data.models.Notification
import com.bashkir.documentstasks.data.models.PerformEntity
import com.bashkir.documentstasks.data.models.TaskEntity
import com.bashkir.documentstasks.data.models.UserEntity

@Database(
    entities = [UserEntity::class, TaskEntity::class, PerformEntity::class, Notification::class],
    version = 2,
//    autoMigrations = [
//        AutoMigration(from = 1, to = 2, spec = AppDatabase.DocumentsTasksAutoMigration::class)
//    ]
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun taskDao(): TaskDao
    abstract fun notificationDao(): NotificationDao

    class DocumentsTasksAutoMigration : AutoMigrationSpec
}