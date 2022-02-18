package com.bashkir.documentstasks.data.models

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

    fun toForm(): UserForm = UserForm(id)
    fun toEntity(): UserEntity = UserEntity(id, firstName, secondName, middleName, email)
}

fun MutableList<User>.toForms(): List<UserForm> = map { it.toForm() }

data class UserForm(
    val id: String
)

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey val id: String,
    val firstName: String,
    val secondName: String,
    val middleName: String?,
    val email: String
) {
    fun toUser(): User = User(id, firstName, secondName, middleName, email)
}