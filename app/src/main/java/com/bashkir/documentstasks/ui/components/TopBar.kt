package com.bashkir.documentstasks.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.bashkir.documentstasks.ui.theme.DocumentsTasksTheme.dimens

@Composable
fun TopBar(titleText: String, navController: NavController, isBackIcon: Boolean = true, leftIcon : @Composable () -> Unit = {}) = TopAppBar(
    elevation = dimens.normalElevation,
    title = {
        Text(titleText)
    },
    navigationIcon = {
        if (isBackIcon)
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Filled.ArrowBack, "Back button")
            }
        else
            leftIcon()
    }
)