package com.bashkir.documentstasks.ui.sreens

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bashkir.documentstasks.ui.navigation.CreateBottomNavHost
import com.bashkir.documentstasks.ui.navigation.MainNavGraphs
import com.bashkir.documentstasks.ui.components.MainBottomNavigationView
import com.bashkir.documentstasks.viewmodels.TasksViewModel

@Composable
fun MainScreenBody(navController: NavHostController, tasksViewModel: TasksViewModel) {

    val bottomNavigationController = rememberNavController()
    val floatingButtonState: MutableState<@Composable () -> Unit> = remember {
        mutableStateOf({ MainNavGraphs.BottomNavGraph.MainFloatingButton(navController) })
    }

    Scaffold(
        bottomBar = {
            MainBottomNavigationView(
                bottomNavController = bottomNavigationController,
                floatingButtonState,
                navController
            )
        },
        floatingActionButton = { floatingButtonState.value() }
    ) {
        CreateBottomNavHost(
            bottomBarNavController = bottomNavigationController,
            navController,
            tasksViewModel
        )
    }
}
