package com.bashkir.documentstasks.ui.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.bashkir.documentstasks.ui.navigation.BottomNavScreen


@Composable
fun MainBottomNavigationView(
    bottomNavController: NavController,
    modifier: Modifier = Modifier
) =
    BottomNavigation(modifier) {
        val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        BottomNavScreen.destinations.forEach { screen ->
            BottomNavigationItem(
                icon = screen.icon ,
                selected = currentDestination?.hierarchy?.any { it.route == screen.destination } == true,
                onClick = {
                    bottomNavController.navigate(screen.destination) {
                        popUpTo(bottomNavController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
