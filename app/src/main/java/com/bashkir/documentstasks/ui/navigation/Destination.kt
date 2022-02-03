package com.bashkir.documentstasks.ui.navigation

import androidx.navigation.NavBackStackEntry

sealed class Destination(var destination: String, private val argumentName: String? = null) {

    init {
        if (argumentName != null)
            destination += "/{$argumentName}"
    }

    fun getArgument(backStackEntry: NavBackStackEntry): String? =
        backStackEntry.arguments?.getString(argumentName)

    fun getIntArgument(backStackEntry: NavBackStackEntry): Int? =
        backStackEntry.arguments?.getInt(argumentName)

    fun destinationWithArgument(argument: String): String =
        destination.replace("{$argumentName}", argument)
}