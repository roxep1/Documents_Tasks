package com.bashkir.documentstasks.ui.sreens

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.bashkir.documentstasks.data.test.testTasksList1
import com.bashkir.documentstasks.ui.components.cards.NotificationCardList
import com.bashkir.documentstasks.ui.components.topbars.TopBar

@Composable
fun NotificationsScreenBody(navController: NavController) = Scaffold(topBar = {
    TopBar(
        titleText = "Уведомления",
        navController = navController
    )
}) {
    NotificationCardList(testTasksList1, navController = navController)
}