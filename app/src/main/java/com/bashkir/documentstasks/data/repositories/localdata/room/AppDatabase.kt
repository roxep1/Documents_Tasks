package com.bashkir.documentstasks.data.repositories.localdata.room

import androidx.room.*
import androidx.room.migration.AutoMigrationSpec
import com.bashkir.documentstasks.data.models.*

@Database(
    entities = [
        UserEntity::class,
        TaskEntity::class,
        PerformEntity::class,
        DocumentEntity::class,
        AgreementEntity::class,
        FamiliarizeEntity::class,
        Notification::class],
    version = 7,
    autoMigrations = [
        AutoMigration(from = 6, to = 7, spec = AppDatabase.DocumentsTasksAutoMigration::class)
    ]
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun taskDao(): TaskDao
    abstract fun notificationDao(): NotificationDao
    abstract fun documentDao(): DocumentDao

    @DeleteColumn(
        tableName = "agreement",
        columnName = "documentdownloadedUri"
    )
    @DeleteColumn(
    tableName = "familiarize",
    columnName = "documentdownloadedUri"
    )
    @DeleteColumn(
    tableName = "document",
    columnName = "downloadedUri"
    )
    class DocumentsTasksAutoMigration : AutoMigrationSpec
}