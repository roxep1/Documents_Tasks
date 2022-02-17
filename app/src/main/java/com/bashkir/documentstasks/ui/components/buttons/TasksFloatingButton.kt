package com.bashkir.documentstasks.ui.components

import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.bashkir.documentstasks.ui.navigation.Screen

@Composable
fun TasksFloatingButton(navController: NavController) = ExtendedFloatingActionButton(
    text = { Text("Новая задача") },
    onClick = {
        navController.navigate(Screen.AddTask.destination)
    },
    icon = {
        androidx.compose.material.Icon(
            Icons.Default.Add,
            contentDescription = "add task button"
        )
    })