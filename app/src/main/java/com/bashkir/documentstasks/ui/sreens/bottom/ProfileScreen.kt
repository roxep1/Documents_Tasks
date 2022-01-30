package com.bashkir.documentstasks.ui.sreens.bottom

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.bashkir.documentstasks.data.test.testUser1
import com.bashkir.documentstasks.ui.components.topbars.TopBarBottomNav
import com.bashkir.documentstasks.ui.theme.DocumentsTasksTheme.dimens
import com.bashkir.documentstasks.ui.theme.normalText
import com.bashkir.documentstasks.ui.theme.titleText

@Composable
fun ProfileScreenBody(navController: NavController) = Scaffold(
    topBar = { TopBarBottomNav(navController = navController, titleText = "Профиль") }
) {
    Column(Modifier.fillMaxSize().padding(dimens.normalPadding)) {
        //TODO
        Text(
            "//TODO",
            style = titleText
        )
        Text(
            testUser1.name.fullName,
            style = titleText,
            modifier = Modifier.padding(bottom = dimens.articlePadding)
        )

        Text(
            testUser1.email,
            style = normalText
        )
    }
}