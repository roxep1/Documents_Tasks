package com.bashkir.documentstasks.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.bashkir.documentstasks.navigation.AuthGraphs.AuthGraph
import com.bashkir.documentstasks.ui.sreens.AuthScreenBody

sealed class AuthGraphs(val route: String) {
    object AuthGraph : AuthGraphs("authGraph") {
        object Auth : Destination("auth")
    }
}

@Composable
fun CreateAuthNavHost(navController: NavHostController, onAuthSuccess: () -> Unit) =
    NavHost(
        navController = navController,
        startDestination = AuthGraph.route
    ) {
        authGraph(onAuthSuccess)
    }

private fun NavGraphBuilder.authGraph(onAuthSuccess: () -> Unit) =
    navigation(AuthGraph.Auth.destination, AuthGraph.route) {
        composable(AuthGraph.Auth.destination) {
            AuthScreenBody(onAuthSuccess)
        }
    }