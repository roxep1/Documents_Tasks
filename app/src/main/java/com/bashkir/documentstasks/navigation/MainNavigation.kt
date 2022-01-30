package com.bashkir.documentstasks.navigation

import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.bashkir.documentstasks.R
import com.bashkir.documentstasks.data.test.testTasksList1
import com.bashkir.documentstasks.navigation.MainGraphs.BottomNavGraph
import com.bashkir.documentstasks.navigation.MainGraphs.MainGraph
import com.bashkir.documentstasks.ui.sreens.AddTaskScreenBody
import com.bashkir.documentstasks.ui.sreens.MainScreenBody
import com.bashkir.documentstasks.ui.sreens.NotificationsScreenBody
import com.bashkir.documentstasks.ui.sreens.TaskDetailScreenBody
import com.bashkir.documentstasks.ui.sreens.bottom.ProfileScreenBody
import com.bashkir.documentstasks.ui.sreens.bottom.TasksScreenBody

sealed class MainGraphs(val route: String) {

    object BottomNavGraph : MainGraphs("BottomNavScreen") {
        sealed class Destination(val destination: String) {

            @Composable
            abstract fun FloatingButton(navController: NavController)

            @Composable
            abstract fun Icon()
        }

        object Tasks : Destination("tasks") {
            @Composable
            override fun FloatingButton(navController: NavController) =
                ExtendedFloatingActionButton(
                    text = { Text("Новая задача") },
                    onClick = {
                        navController.navigate(MainGraph.AddTask.destination)
                    },
                    icon = { Icon(Icons.Default.Add, contentDescription = "add task button") })

            @Composable
            override fun Icon() = Icon(painterResource(id = R.drawable.ic_tasks), contentDescription = null)

        }

        object Profile : Destination("profile") {

            @Composable
            override fun FloatingButton(navController: NavController) {}

            @Composable
            override fun Icon() = Icon(Icons.Default.Person, contentDescription = null)
        }

        val mainDestination: Destination = Tasks
        val destinations: List<Destination> = listOf(Tasks, Profile)

        @Composable
        fun MainFloatingButton(navController: NavController) = mainDestination.FloatingButton(
            navController = navController
        )
    }

    object MainGraph : MainGraphs("mainGraph") {
        object BottomNav : Destination("main_screen")
        object TaskDetail : Destination("task_detail", "taskId")
        object Notifications : Destination("notifications")
        object AddTask : Destination("add_task")
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

        composable(MainGraph.Notifications.destination) {
            NotificationsScreenBody(navController = navController)
        }

        composable(MainGraph.AddTask.destination) {
            AddTaskScreenBody(navController = navController)
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

        navigation(BottomNavGraph.mainDestination.destination, BottomNavGraph.route) {
            composable(BottomNavGraph.Tasks.destination) {
                TasksScreenBody(navController = mainNavController)
            }

            composable(BottomNavGraph.Profile.destination){
                ProfileScreenBody(navController = mainNavController)
            }
        }
    }