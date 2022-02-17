package com.bashkir.documentstasks.ui.components.topbars

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import com.bashkir.documentstasks.ui.components.SearchTextField
import com.bashkir.documentstasks.ui.theme.DocumentsTasksTheme.dimens

@Composable
fun TopBar(
    titleText: String,
    navController: NavController,
    isBackIcon: Boolean = true,
    leftIcon: @Composable () -> Unit = {},
    searchTextState: MutableState<TextFieldValue>? = null,
    actions : @Composable RowScope.() -> Unit = {}
) = TopAppBar(
    elevation = dimens.normalElevation,
    title = {
        if (searchTextState == null)
            Text(titleText)
        else SearchTextField(searchTextState = searchTextState)
    },
    navigationIcon = {
        if (isBackIcon)
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Filled.ArrowBack, "Back button")
            }
        else
            leftIcon()
    },
    actions = actions
)