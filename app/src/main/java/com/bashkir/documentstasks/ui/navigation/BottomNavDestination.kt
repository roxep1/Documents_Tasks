package com.bashkir.documentstasks.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

sealed class BottomNavDestination(val destination: String) {
    @Composable
    abstract fun FloatingButton(navController: NavController)

    @Composable
    abstract fun Icon()
}