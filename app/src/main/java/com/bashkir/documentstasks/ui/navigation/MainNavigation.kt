package com.bashkir.documentstasks.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bashkir.documentstasks.ui.sreens.*
import com.bashkir.documentstasks.ui.sreens.bottom.DocumentsScreenBody
import com.bashkir.documentstasks.ui.sreens.bottom.ProfileScreenBody
import com.bashkir.documentstasks.ui.sreens.bottom.TasksScreenBody
import com.bashkir.documentstasks.viewmodels.*

@Composable
fun CreateMainNavHost(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    tasksViewModel: TasksViewModel,
    profileViewModel: ProfileViewModel,
    notificationsViewModel: NotificationsViewModel,
    documentsViewModel: DocumentsViewModel
) =
    NavHost(
        navController = navController,
        startDestination = Screen.Auth.destination
    ) {
        composable(Screen.Auth.destination) {
            AuthScreenBody(authViewModel, navController)
        }

        composable(Screen.BottomNav.destination) {
            MainScreenBody(navController, tasksViewModel, profileViewModel, documentsViewModel)
        }

        composable(Screen.TaskDetail.destinationWithArgument()) {
            TaskDetailScreenBody(
                Screen.TaskDetail.getArgument(it)!!.toInt(),
                navController,
                tasksViewModel
            )
        }

        composable(Screen.DocumentDetail.destinationWithArgument()) {
            DocumentDetailScreenBody(
                Screen.DocumentDetail.getArgument(it)!!.toInt(),
                navController,
                documentsViewModel
            )
        }

        composable(Screen.Notifications.destination) {
            NotificationsScreenBody(navController, notificationsViewModel)
        }

        composable(Screen.AddTask.destination) {
            AddTaskScreenBody(navController, tasksViewModel)
        }

        composable(Screen.AddDocument.destination) {
            AddDocumentScreenBody(navController, documentsViewModel)
        }
    }

@Composable
fun CreateBottomNavHost(
    bottomBarNavController: NavHostController,
    mainNavController: NavHostController,
    tasksViewModel: TasksViewModel,
    profileViewModel: ProfileViewModel,
    documentsViewModel: DocumentsViewModel
) =
    NavHost(
        navController = bottomBarNavController,
        startDestination = BottomNavScreen.mainDestination.destination
    ) {
        composable(BottomNavScreen.Documents.destination) {
            DocumentsScreenBody(navController = mainNavController, documentsViewModel)
        }

        composable(BottomNavScreen.Tasks.destination) {
            TasksScreenBody(navController = mainNavController, tasksViewModel)
        }

        composable(BottomNavScreen.Profile.destination) {
            ProfileScreenBody(navController = mainNavController, profileViewModel)
        }
    }