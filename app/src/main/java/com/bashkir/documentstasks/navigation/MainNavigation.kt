package com.bashkir.documentstasks.navigation

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.bashkir.documentstasks.R
import com.bashkir.documentstasks.navigation.MainGraphs.BottomNavGraph
import com.bashkir.documentstasks.navigation.MainGraphs.MainGraph
import com.bashkir.documentstasks.ui.sreens.MainScreenBody

sealed class MainGraphs(val route: String) {
    object BottomNavGraph : MainGraphs("BottomNavScreen") {
        sealed class Destination(val destination: String, @DrawableRes val resourceId: Int)

        object Tasks : Destination("tasks", R.drawable.ic_tasks)
        object TasksProgress : Destination("tasksProgress", R.drawable.ic_tasks_progress)

        val destinations = listOf(Tasks, TasksProgress)
    }

    object MainGraph : MainGraphs("mainGraph") {
        object BottomNav : Destination("MainScreen")
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
    }

@Composable
fun CreateBottomNavHost(navController: NavHostController) =
    NavHost(
        navController = navController,
        startDestination = BottomNavGraph.route
    ) {

        navigation(BottomNavGraph.Tasks.destination, BottomNavGraph.route) {
            composable(BottomNavGraph.Tasks.destination) {

            }

            composable(BottomNavGraph.TasksProgress.destination) {

            }
        }

    }