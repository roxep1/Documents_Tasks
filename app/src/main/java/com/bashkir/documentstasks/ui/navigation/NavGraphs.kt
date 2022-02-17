package com.bashkir.documentstasks.ui.navigation

import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavBackStackEntry
import com.bashkir.documentstasks.R

sealed class BottomNavScreen(val destination: String, val icon: @Composable () -> Unit) {

    object Tasks : BottomNavScreen(
        "tasks",
        { Icon(painterResource(id = R.drawable.ic_tasks), contentDescription = null) }
    )

    object Profile : BottomNavScreen(
        "profile",
        { Icon(Icons.Default.Person, contentDescription = null) }
    )

    companion object{
        val mainDestination: BottomNavScreen = Tasks
        val destinations: List<BottomNavScreen> = listOf(Tasks, Profile)
    }
}

sealed class Screen(var destination: String, private val argumentName: String? = null) {

    object BottomNav : Screen("main_screen")
    object TaskDetail : Screen("task_detail", "taskId")
    object Notifications : Screen("notifications")
    object AddTask : Screen("add_task")
    object Auth : Screen("auth")

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