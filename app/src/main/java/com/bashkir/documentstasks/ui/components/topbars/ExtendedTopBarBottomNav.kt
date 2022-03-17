package com.bashkir.documentstasks.ui.components.topbars

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController

@Composable
fun ExtendedTopBarBottomNav(
    navController: NavController,
    searchTextField: MutableState<TextFieldValue>,
    filterSettingsVisible: MutableState<Boolean>
) = TopBarBottomNav(
    navController = navController,
    searchTextFieldValue = searchTextField,
    filterSettingsVisible = filterSettingsVisible
)