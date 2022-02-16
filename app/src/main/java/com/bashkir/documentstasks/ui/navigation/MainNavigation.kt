package com.bashkir.documentstasks.ui.navigation

import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bashkir.documentstasks.R
import com.bashkir.documentstasks.ui.navigation.MainNavGraphs.BottomNavGraph
import com.bashkir.documentstasks.ui.navigation.MainNavGraphs.MainGraph
import com.bashkir.documentstasks.ui.sreens.*
import com.bashkir.documentstasks.ui.sreens.bottom.ProfileScreenBody
import com.bashkir.documentstasks.ui.sreens.bottom.TasksScreenBody
import com.bashkir.documentstasks.viewmodels.AuthViewModel
import com.bashkir.documentstasks.viewmodels.TasksViewModel

sealed class MainNavGraphs {

    object BottomNavGraph : MainNavGraphs() {

        val mainDestination: BottomNavDestination = Tasks
        val destinations: List<BottomNavDestination> = listOf(Tasks, Profile)

        @Composable
        fun MainFloatingButton(navController: NavController) = mainDestination.FloatingButton(
            navController = navController
        )

        object Tasks : BottomNavDestination("tasks") {
            @Composable
            override fun FloatingButton(navController: NavController) =
                ExtendedFloatingActionButton(
                    text = { Text("Новая задача") },
                    onClick = {
                        navController.navigate(MainGraph.AddTask.destination)
                    },
                    icon = { Icon(Icons.Default.Add, contentDescription = "add task button") })

            @Composable
            override fun Icon() =
                Icon(painterResource(id = R.drawable.ic_tasks), contentDescription = null)
        }

        object Profile : BottomNavDestination("profile") {
            @Composable
            override fun FloatingButton(navController: NavController) {
            }

            @Composable
            override fun Icon() = Icon(Icons.Default.Person, contentDescription = null)
        }
    }

    object MainGraph : MainNavGraphs() {
        object BottomNav : Destination("main_screen")
        object TaskDetail : Destination("task_detail", "taskId")
        object Notifications : Destination("notifications")
        object AddTask : Destination("add_task")
        object Auth : Destination("auth")
    }
}

@Composable
fun CreateMainNavHost(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    tasksViewModel: TasksViewModel
) =
    NavHost(
        navController = navController,
        startDestination = MainGraph.Auth.destination
    ) {
        composable(MainGraph.Auth.destination) {
            AuthScreenBody(authViewModel, navController)
        }

        composable(MainGraph.BottomNav.destination) {
            MainScreenBody(navController = navController, tasksViewModel)
        }

        composable(MainGraph.TaskDetail.destination) {
            val taskId = MainGraph.TaskDetail.getIntArgument(it)

            TaskDetailScreenBody(
                taskId = taskId!!,
                navController = navController,
                tasksViewModel
            )
        }

        composable(MainGraph.Notifications.destination) {
            NotificationsScreenBody(navController = navController)
        }

        composable(MainGraph.AddTask.destination) {
            AddTaskScreenBody(navController = navController, tasksViewModel)
        }
    }

@Composable
fun CreateBottomNavHost(
    bottomBarNavController: NavHostController,
    mainNavController: NavHostController,
    tasksViewModel: TasksViewModel
) =
    NavHost(
        navController = bottomBarNavController,
        startDestination = BottomNavGraph.mainDestination.destination
    ) {
        composable(BottomNavGraph.Tasks.destination) {
            TasksScreenBody(navController = mainNavController, tasksViewModel)
        }

        composable(BottomNavGraph.Profile.destination) {
            ProfileScreenBody(navController = mainNavController)
        }
    }