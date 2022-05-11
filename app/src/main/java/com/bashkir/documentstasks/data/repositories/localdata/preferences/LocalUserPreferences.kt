package com.bashkir.documentstasks.data.repositories.localdata.preferences

import android.content.Context
import android.net.Uri
import androidx.core.content.edit

class LocalUserPreferences(context: Context) {
    companion object {
        private const val localUserPath = "localUsers"
    }

    private val localUsers = context.getSharedPreferences(localUserPath, Context.MODE_PRIVATE)

    val authorizedId: String
        get() = localUsers.all.filter { it.value as Boolean }.keys.first()

    fun authorizeUser(id: String) = localUsers.edit {
        getAllUsersId().forEach {
            putBoolean(it, false)
        }

        putBoolean(id, true)
        commit()
    }

    fun logoutUser() = localUsers.edit {
        putBoolean(authorizedId, false)
        commit()
    }

    private fun getAllUsersId(): List<String> = localUsers.all.keys.map { it }

    fun getAuthorizedIdIfExist(): String? = localUsers.all.filter { it.value as Boolean }.keys.run {
        if (isNotEmpty())
            first()
        else null
    }
}