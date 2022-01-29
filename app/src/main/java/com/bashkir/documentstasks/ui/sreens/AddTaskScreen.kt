package com.bashkir.documentstasks.ui.sreens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import com.bashkir.documentstasks.ui.components.TopBar
import com.bashkir.documentstasks.ui.theme.DocumentsTasksTheme.dimens
import com.bashkir.documentstasks.ui.theme.placeHolderText

@Composable
fun AddTaskScreenView(navController: NavController) = Scaffold(topBar = {
    TopBar(
        titleText = "Новое задание",
        navController = navController
    )
}) {
    var taskTitle by remember { mutableStateOf(TextFieldValue()) }
    var taskDesc by remember { mutableStateOf(TextFieldValue()) }

    Column(
        Modifier
            .fillMaxSize()
            .padding(dimens.normalPadding)
    ) {
        OutlinedTextField(
            modifier = Modifier.padding(bottom = dimens.normalPadding),
            value = taskTitle,
            onValueChange = { taskTitle = it },
            placeholder = { Text("Название", style = placeHolderText) }
        )

        OutlinedTextField(
            value = taskDesc,
            onValueChange = { taskDesc = it },
            modifier = Modifier.padding(bottom = dimens.normalPadding),
            placeholder = { Text("Описание", style = placeHolderText) }
        )

    }
}