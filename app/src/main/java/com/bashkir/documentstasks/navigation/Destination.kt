package com.bashkir.documentstasks.navigation

import androidx.navigation.NavBackStackEntry

sealed class Destination(private val baseDestination: String, private val argumentName: String? = null) {
    var destination = baseDestination

    init {
        if (argumentName != null)
            destination += "/{$argumentName}"
    }

    fun getArgument(backStackEntry: NavBackStackEntry): String? =
        backStackEntry.arguments?.getString(argumentName)

    fun destinationWithArgument(argument: String) : String = "$baseDestination/$argument"

}