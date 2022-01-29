package com.bashkir.documentstasks.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import com.bashkir.documentstasks.ui.theme.DocumentsTasksTheme.dimens
import com.bashkir.documentstasks.ui.theme.normalText

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
        else SearchView(searchTextState = searchTextState)
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