package com.bashkir.documentstasks.navigation

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.bashkir.documentstasks.R
import com.bashkir.documentstasks.data.test.testTasksList1
import com.bashkir.documentstasks.navigation.MainGraphs.BottomNavGraph
import com.bashkir.documentstasks.navigation.MainGraphs.MainGraph
import com.bashkir.documentstasks.ui.sreens.MainScreenBody
import com.bashkir.documentstasks.ui.sreens.NotificationsScreenBody
import com.bashkir.documentstasks.ui.sreens.TaskDetailScreenBody
import com.bashkir.documentstasks.ui.sreens.bottom.TasksScreenBody

sealed class MainGraphs(val route: String) {
    object BottomNavGraph : MainGraphs("BottomNavScreen") {
        sealed class Destination(val destination: String, @DrawableRes val resourceId: Int)

        object Tasks : Destination("tasks", R.drawable.ic_tasks)

        val destinations = listOf(Tasks)
    }

    object MainGraph : MainGraphs("mainGraph") {
        object BottomNav : Destination("main_screen")
        object TaskDetail : Destination("task_detail", "taskId")
        object Notifications : Destination("notifications")
    }
}

@Composable
fun CreateMainNavHost(navController: NavHostController) =
    NavHost(
        navController = navController,
        startDestination = MainGraph.route
    ) {
        mainGraph(navController)
    }

private fun NavGraphBuilder.mainGraph(navController: NavHostController) =
    navigation(MainGraph.BottomNav.destination, MainGraph.route) {
        composable(MainGraph.BottomNav.destination) {
            MainScreenBody(navController = navController)
        }

        composable(MainGraph.TaskDetail.destination) {
            val taskId = MainGraph.TaskDetail.getArgument(it)?.toInt()
            TaskDetailScreenBody(
                task = testTasksList1.find { task -> task.id == taskId }!!,
                navController = navController
            )
        }

        composable(MainGraph.Notifications.destination){
            NotificationsScreenBody(navController = navController)
        }
    }

@Composable
fun CreateBottomNavHost(
    bottomBarNavController: NavHostController,
    mainNavController: NavHostController
) =
    NavHost(
        navController = bottomBarNavController,
        startDestination = BottomNavGraph.route
    ) {

        navigation(BottomNavGraph.Tasks.destination, BottomNavGraph.route) {
            composable(BottomNavGraph.Tasks.destination) {
                TasksScreenBody(navController = mainNavController)
            }
        }

    }