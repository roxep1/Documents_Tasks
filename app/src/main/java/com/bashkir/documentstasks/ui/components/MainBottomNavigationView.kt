package com.bashkir.documentstasks.ui.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.bashkir.documentstasks.navigation.MainGraphs


@Composable
fun MainBottomNavigationView(navController: NavController) =
    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        MainGraphs.BottomNavGraph.destinations.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(painterResource(id = screen.resourceId), contentDescription = null) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.destination } == true,
                onClick = {
                    navController.navigate(screen.destination) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
