package com.bashkir.documentstasks.ui.sreens.bottom

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.airbnb.mvrx.Uninitialized
import com.airbnb.mvrx.compose.collectAsState
import com.bashkir.documentstasks.ui.components.AsyncView
import com.bashkir.documentstasks.ui.components.topbars.TopBarBottomNav
import com.bashkir.documentstasks.ui.theme.DocumentsTasksTheme.dimens
import com.bashkir.documentstasks.ui.theme.normalText
import com.bashkir.documentstasks.ui.theme.titleText
import com.bashkir.documentstasks.utils.logoutNavigate
import com.bashkir.documentstasks.viewmodels.ProfileViewModel

@Composable
fun ProfileScreenBody(navController: NavController, viewModel: ProfileViewModel) = Scaffold(
    topBar = { TopBarBottomNav(navController = navController, titleText = "Профиль") }
) { paddingValues ->
    val user by viewModel.collectAsState { it.user }
    Column(
        Modifier
            .fillMaxSize()
            .padding(dimens.normalPadding)
    ) {
        //TODO
        Text(
            "//TODO",
            style = titleText
        )
        AsyncView(user, errorText = "Не удалось загрузить авторизованного пользователя") {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Text(
                    it.fullName,
                    style = titleText,
                    modifier = Modifier.padding(bottom = dimens.articlePadding)
                )
                Text(
                    it.email,
                    style = normalText
                )

                OutlinedButton(
                    onClick = viewModel::logout,
                    modifier = Modifier
                        .padding(dimens.normalPadding)
                        .align(Alignment.BottomCenter)
                ) {
                    Text("Выйти", style = titleText)
                }
            }
        }

        LaunchedEffect(user) {
            if (user is Uninitialized)
                navController.logoutNavigate()
        }
    }
}