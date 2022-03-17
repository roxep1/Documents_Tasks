package com.bashkir.documentstasks.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class User(
    val id: String,
    val firstName: String,
    val secondName: String,
    val middleName: String?,
    val email: String
) {
    val fullName: String
        get() = "$secondName $firstName ${middleName ?: ""}"

    val shortFullName: String
        get() = "$secondName ${firstName[0]}. ${middleName?.get(0)?.plus(".") ?: ""}"

    fun toForm(): UserForm = UserForm(id)
    fun toEntity(): UserEntity = UserEntity(id, firstName, secondName, middleName, email)
}

fun MutableList<User>.toForms(): List<UserForm> = map { it.toForm() }

data class UserForm(
    val id: String
)

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey val userId: String,
    @ColumnInfo(defaultValue = "") val firstName: String,
    val secondName: String,
    val middleName: String?,
    val email: String
) {
    fun toUser(): User = User(userId, firstName, secondName, middleName, email)
}