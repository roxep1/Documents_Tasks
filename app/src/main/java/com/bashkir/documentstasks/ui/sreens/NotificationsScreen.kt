package com.bashkir.documentstasks.ui.sreens

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.airbnb.mvrx.compose.collectAsState
import com.bashkir.documentstasks.ui.components.cards.NotificationCardList
import com.bashkir.documentstasks.ui.components.topbars.TopBar
import com.bashkir.documentstasks.ui.components.views.AsyncView
import com.bashkir.documentstasks.viewmodels.NotificationsViewModel

@Composable
fun NotificationsScreenBody(navController: NavController, viewModel: NotificationsViewModel) =
    Scaffold(topBar = {
        TopBar(
            titleText = "Уведомления",
            navController = navController
        )
    }) {
        val notifications by viewModel.collectAsState { it.notifications }
        AsyncView(
            async = notifications,
            errorText = "Не удалось загрузить уведомления. Ошибка внутреннего хранилища",
            onUpdate = {}
        ) {loadedNotifications, _ ->
            NotificationCardList(
                loadedNotifications,
                navController = navController,
                onDelete = viewModel::deleteNotification
            )
        }
    }