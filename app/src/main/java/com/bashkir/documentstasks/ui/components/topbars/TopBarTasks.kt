package com.bashkir.documentstasks.ui.components.topbars

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import com.bashkir.documentstasks.R

@Composable
fun TopBarTasks(
    navController: NavController,
    searchTextField: MutableState<TextFieldValue>,
    filterSettingsVisible: MutableState<Boolean>
) = TopBarBottomNav(
    navController = navController,
    searchTextFieldValue = searchTextField,
    actions = {
        IconButton(onClick = {
            filterSettingsVisible.value = !filterSettingsVisible.value
        }) {
            Icon(
                if (filterSettingsVisible.value) painterResource(R.drawable.ic_arrow_up)
                else painterResource(R.drawable.ic_arrow_down),
                ""
            )
        }
    }
)