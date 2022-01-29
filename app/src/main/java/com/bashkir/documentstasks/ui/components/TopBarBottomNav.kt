package com.bashkir.documentstasks.ui.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.bashkir.documentstasks.R
import com.bashkir.documentstasks.navigation.MainGraphs

@Composable
fun TopBarBottomNav(navController: NavController) =
    TopBar(
        titleText = stringResource(R.string.tasks_screen_title),
        navController = navController,
        isBackIcon = false,
        leftIcon = { NotificationsIcon(navController = navController) }
    )

@Composable
private fun NotificationsIcon(navController: NavController) =
    IconButton(onClick = {navController.navigate(MainGraphs.MainGraph.Notifications.destination)}){
        Icon(painterResource(R.drawable.ic_notifications), "notifications")
    }

