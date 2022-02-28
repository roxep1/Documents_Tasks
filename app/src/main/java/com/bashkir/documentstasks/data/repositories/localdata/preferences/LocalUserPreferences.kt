package com.bashkir.documentstasks.data.repositories.localdata.preferences

import android.content.Context
import androidx.core.content.edit

class LocalUserPreferences(context: Context) {
    companion object {
        private const val path = "localUsers"
    }

    private val sharedPref = context.getSharedPreferences(path, Context.MODE_PRIVATE)

    fun authorizeUser(id: String) = sharedPref.edit {
        getAllUsersId().forEach {
            putBoolean(it, false)
        }

        putBoolean(id, true)
        commit()
    }

    fun logoutUser() = sharedPref.edit{
        putBoolean(getAuthorizedId(), false)
        commit()
    }

    private fun getAllUsersId(): List<String> = sharedPref.all.keys.map { it }

    fun getAuthorizedIdIfExist(): String? = sharedPref.all.filter { it.value as Boolean }.keys.run {
        if (isNotEmpty())
            first()
        else null
    }

    fun getAuthorizedId(): String = sharedPref.all.filter { it.value as Boolean }.keys.first()
}