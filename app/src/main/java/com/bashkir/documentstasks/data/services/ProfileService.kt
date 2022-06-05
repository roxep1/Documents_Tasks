package com.bashkir.documentstasks.data.services

import com.bashkir.documentstasks.data.models.User

class ProfileService : SharedService() {

    suspend fun logout() {
        preferences.logoutUser()
        api.logout()
    }

    suspend fun getAuthorizedUser(): User =
        userDao.getLocalUser(preferences.authorizedId).toUser()
}