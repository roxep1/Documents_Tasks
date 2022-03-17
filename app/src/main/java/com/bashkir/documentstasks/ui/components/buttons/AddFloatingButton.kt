package com.bashkir.documentstasks.ui.components.buttons

import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun AddFloatingButton(navController: NavController, text: String, navDestination: String) =
    ExtendedFloatingActionButton(
        text = { Text(text) },
        onClick = {
            navController.navigate(navDestination)
        },
        icon = {
            androidx.compose.material.Icon(
                Icons.Default.Add,
                contentDescription = "add button"
            )
        })