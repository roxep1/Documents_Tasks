package com.bashkir.documentstasks.ui.sreens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bashkir.documentstasks.ui.components.MainBottomNavigationView
import com.bashkir.documentstasks.ui.navigation.CreateBottomNavHost
import com.bashkir.documentstasks.viewmodels.DocumentsViewModel
import com.bashkir.documentstasks.viewmodels.ProfileViewModel
import com.bashkir.documentstasks.viewmodels.TasksViewModel

@Composable
fun MainScreenBody(
    navController: NavHostController,
    tasksViewModel: TasksViewModel,
    profileViewModel: ProfileViewModel,
    documentsViewModel: DocumentsViewModel
) {
    OnCreate(tasksViewModel, profileViewModel, documentsViewModel)

    val bottomNavigationController = rememberNavController()

    Scaffold(
        bottomBar = {
            MainBottomNavigationView(
                bottomNavController = bottomNavigationController
            )
        }
    ) {
        Box(Modifier.padding(it)) {
            CreateBottomNavHost(
                bottomNavigationController,
                navController,
                tasksViewModel,
                profileViewModel,
                documentsViewModel
            )
        }
    }
}

@Composable
private fun OnCreate(
    tasksViewModel: TasksViewModel,
    profileViewModel: ProfileViewModel,
    documentsViewModel: DocumentsViewModel
) = LaunchedEffect(true) {
    tasksViewModel.onCreate()
    documentsViewModel.onCreate()
    profileViewModel.getAuthorizedUser()
}