package com.bashkir.documentstasks.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bashkir.documentstasks.ui.sreens.*
import com.bashkir.documentstasks.ui.sreens.bottom.ProfileScreenBody
import com.bashkir.documentstasks.ui.sreens.bottom.TasksScreenBody
import com.bashkir.documentstasks.viewmodels.AuthViewModel
import com.bashkir.documentstasks.viewmodels.NotificationsViewModel
import com.bashkir.documentstasks.viewmodels.ProfileViewModel
import com.bashkir.documentstasks.viewmodels.TasksViewModel

@Composable
fun CreateMainNavHost(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    tasksViewModel: TasksViewModel,
    profileViewModel: ProfileViewModel,
    notificationsViewModel: NotificationsViewModel
) =
    NavHost(
        navController = navController,
        startDestination = Screen.Auth.destination
    ) {
        composable(Screen.Auth.destination) {
            AuthScreenBody(authViewModel, navController)
        }

        composable(Screen.BottomNav.destination) {
            MainScreenBody(navController = navController, tasksViewModel, profileViewModel)
        }

        composable(Screen.TaskDetail.destinationWithArgument()) {
            val taskId = Screen.TaskDetail.getArgument(it)!!.toInt()

            TaskDetailScreenBody(
                taskId,
                navController,
                tasksViewModel
            )
        }

        composable(Screen.Notifications.destination) {
            NotificationsScreenBody(navController, notificationsViewModel)
        }

        composable(Screen.AddTask.destination) {
            AddTaskScreenBody(navController, tasksViewModel)
        }
    }

@Composable
fun CreateBottomNavHost(
    bottomBarNavController: NavHostController,
    mainNavController: NavHostController,
    tasksViewModel: TasksViewModel,
    profileViewModel: ProfileViewModel
) =
    NavHost(
        navController = bottomBarNavController,
        startDestination = BottomNavScreen.mainDestination.destination
    ) {
        composable(BottomNavScreen.Tasks.destination) {
            TasksScreenBody(navController = mainNavController, tasksViewModel)
        }

        composable(BottomNavScreen.Profile.destination) {
            ProfileScreenBody(navController = mainNavController, profileViewModel)
        }
    }