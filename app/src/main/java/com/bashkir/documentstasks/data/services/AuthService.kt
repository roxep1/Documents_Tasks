package com.bashkir.documentstasks.data.services

import com.bashkir.documentstasks.data.repositories.localdata.room.UserDao
import org.koin.java.KoinJavaComponent.inject

class AuthService : SharedService() {
    private val userDao: UserDao by inject(UserDao::class.java)

    suspend fun authorizeUser(userId: String): String {
        val user = api.getUser(userId)
        userDao.addLocalUser(user.toEntity())
        if (!isAuthorized(user.id))
            preferences.authorizeUser(user.id)
        return user.id
    }

    private fun isAuthorized(userId: String): Boolean =
        preferences.getAuthorizedIdIfExist() == userId
}