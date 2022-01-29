package com.bashkir.documentstasks.ui.sreens

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bashkir.documentstasks.navigation.CreateBottomNavHost
import com.bashkir.documentstasks.ui.components.MainBottomNavigation

@Composable
fun MainScreenBody(navController: NavHostController) {
    val bottomNavigationController = rememberNavController()
    Scaffold(
        bottomBar = { MainBottomNavigation(navController = bottomNavigationController) }
    ) {
        CreateBottomNavHost(bottomBarNavController = bottomNavigationController, navController)
    }
}