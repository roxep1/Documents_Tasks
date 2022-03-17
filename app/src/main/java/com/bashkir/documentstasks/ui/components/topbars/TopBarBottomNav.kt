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
import com.bashkir.documentstasks.ui.navigation.Screen

@Composable
fun TopBarBottomNav(
    navController: NavController,
    titleText : String = "",
    searchTextFieldValue: MutableState<TextFieldValue>? = null,
    filterSettingsVisible: MutableState<Boolean>? = null
) =
    TopBar(
        titleText = titleText,
        navController = navController,
        isBackIcon = false,
        leftIcon = { NotificationsIcon(navController = navController) },
        searchTextState = searchTextFieldValue,
        actions = {
            filterSettingsVisible?.let{
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
        }
    )

@Composable
private fun NotificationsIcon(navController: NavController) =
    IconButton(onClick = { navController.navigate(Screen.Notifications.destination) }) {
        Icon(painterResource(R.drawable.ic_notifications), "notifications")
    }