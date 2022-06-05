package com.bashkir.documentstasks.data.services

class AuthService : SharedService() {

    suspend fun authorizeUser(userId: String, idToken: String): String {
         if (preferences.getAuthorizedIdIfExist() == userId && !isOnline)
            return userId
        val user = api.login(idToken)
        userDao.addLocalUser(user.toEntity())
        if (!isAuthorized(user.id))
            preferences.authorizeUser(user.id)
        return user.id
    }

    private fun isAuthorized(userId: String): Boolean =
        preferences.getAuthorizedIdIfExist() == userId
}