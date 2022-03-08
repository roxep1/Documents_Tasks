package com.bashkir.documentstasks.data.services

import com.bashkir.documentstasks.data.models.User

class ProfileService : SharedService() {

    fun logout() {
        preferences.logoutUser()
    }

    suspend fun getAuthorizedUser(): User =
        userDao.getLocalUser(preferences.authorizedId).toUser()
}