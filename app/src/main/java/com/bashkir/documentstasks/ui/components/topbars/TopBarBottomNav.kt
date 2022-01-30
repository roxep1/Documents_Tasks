package com.bashkir.documentstasks.ui.components.topbars

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import com.bashkir.documentstasks.R
import com.bashkir.documentstasks.navigation.MainGraphs

@Composable
fun TopBarBottomNav(
    navController: NavController,
    titleText : String = "",
    searchTextFieldValue: MutableState<TextFieldValue>? = null,
    actions : @Composable RowScope.() -> Unit = {}
) =
    TopBar(
        titleText = titleText,
        navController = navController,
        isBackIcon = false,
        leftIcon = { NotificationsIcon(navController = navController) },
        searchTextState = searchTextFieldValue,
        actions = actions
    )

@Composable
private fun NotificationsIcon(navController: NavController) =
    IconButton(onClick = { navController.navigate(MainGraphs.MainGraph.Notifications.destination) }) {
        Icon(painterResource(R.drawable.ic_notifications), "notifications")
    }

