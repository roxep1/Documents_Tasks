package com.bashkir.documentstasks.ui.navigation

import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavBackStackEntry
import com.bashkir.documentstasks.R

sealed class BottomNavScreen(val destination: String, val icon: @Composable () -> Unit) {

    object Tasks : BottomNavScreen(
        "tasks",
        icon = { Icon(painterResource(id = R.drawable.ic_tasks), null) }
    )

    object Profile : BottomNavScreen(
        "profile",
        icon = { Icon(Icons.Default.Person, null) }
    )

    object Documents : BottomNavScreen(
        "documents",
        icon = { Icon(Icons.Default.List, null)}
    )

    companion object{
        val mainDestination: BottomNavScreen = Tasks
        val destinations: List<BottomNavScreen> = listOf(Documents, Tasks, Profile)
    }
}

sealed class Screen(val destination: String, private val argumentName: String? = null) {

    object BottomNav : Screen("main_screen")
    object TaskDetail : Screen("task_detail", "taskId")
    object DocumentDetail : Screen("document_detail", "documentId")
    object Notifications : Screen("notifications")
    object AddTask : Screen("add_task")
    object AddDocument : Screen("add_document")
    object Auth : Screen("auth")

    fun getArgument(backStackEntry: NavBackStackEntry): String? =
        backStackEntry.arguments?.getString(argumentName)

    fun getIntArgument(backStackEntry: NavBackStackEntry): Int? =
        backStackEntry.arguments?.getInt(argumentName)

    fun destinationWithArgument(argument: String): String =
        "$destination/$argument"

    fun destinationWithArgument(): String =
        "$destination/{$argumentName}"
}