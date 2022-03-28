package com.bashkir.documentstasks.ui.sreens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import com.airbnb.mvrx.compose.collectAsState
import com.bashkir.documentstasks.data.models.PerformForm
import com.bashkir.documentstasks.data.models.TaskForm
import com.bashkir.documentstasks.data.models.User
import com.bashkir.documentstasks.data.models.toForms
import com.bashkir.documentstasks.ui.components.cards.UsersList
import com.bashkir.documentstasks.ui.components.dialogs.AddUserDialog
import com.bashkir.documentstasks.ui.components.dialogs.DatePickerDialog
import com.bashkir.documentstasks.ui.components.dialogs.TimePickerDialog
import com.bashkir.documentstasks.ui.components.topbars.TopBar
import com.bashkir.documentstasks.ui.components.views.DeadlineView
import com.bashkir.documentstasks.ui.theme.DocumentsTasksTheme.dimens
import com.bashkir.documentstasks.ui.theme.placeHolderText
import com.bashkir.documentstasks.ui.theme.titleText
import com.bashkir.documentstasks.utils.plus
import com.bashkir.documentstasks.viewmodels.TasksState
import com.bashkir.documentstasks.viewmodels.TasksViewModel
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDateTime

@Composable
fun AddTaskScreenBody(navController: NavController, viewModel: TasksViewModel) = Scaffold(topBar = {
    TopBar(
        titleText = "Новое задание",
        navController = navController
    )
}) {
    var taskTitle by remember { mutableStateOf(TextFieldValue()) }
    var taskDesc by remember { mutableStateOf(TextFieldValue()) }
    var taskDeadLine by remember { mutableStateOf(LocalDateTime.now()) }
    val taskPerformers = remember { mutableStateListOf<User>() }

    val dateDialogState = rememberMaterialDialogState()
    val timeDialogState = rememberMaterialDialogState()
    val usersDialogState = rememberMaterialDialogState()

    val users by viewModel.collectAsState(TasksState::users)

    fun taskIsValid(): Boolean = taskDeadLine.isAfter(LocalDateTime.now()) &&
            taskTitle.text.isNotBlank() && taskTitle.text.isNotEmpty()
            && taskDesc.text.isNotEmpty() && taskDesc.text.isNotBlank()
            && taskPerformers.isNotEmpty()

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
            modifier = Modifier.padding(bottom = dimens.normalPadding),
            value = taskDesc,
            onValueChange = { taskDesc = it },
            placeholder = { Text("Описание", style = placeHolderText) }
        )

        DeadlineView(
            dateDialogState = dateDialogState,
            timeDialogState = timeDialogState,
            taskDeadLine = taskDeadLine
        )

        DatePickerDialog(dialogState = dateDialogState) {
            taskDeadLine = it.atStartOfDay()
        }

        TimePickerDialog(dialogState = timeDialogState) {
            taskDeadLine = taskDeadLine.plus(it)
        }

        AddUserDialog(usersDialogState, taskPerformers, users)

        UsersList(users = taskPerformers, deleteUserOnClick = taskPerformers::remove, label = "Исполнители") {
            Row(Modifier.fillParentMaxWidth(), horizontalArrangement = Arrangement.Center) {
                OutlinedButton(onClick = {
                    usersDialogState.show()
                }) {
                    Text("Добавить исполнителя")
                }
            }
        }

        Button(
            onClick = {
                viewModel.addTask(
                    TaskForm(
                        taskTitle.text,
                        taskDesc.text,
                        taskDeadLine,
                        taskPerformers.toForms().map { PerformForm(it) }
                    )
                )
                navController.popBackStack()
            },
            Modifier
                .fillMaxWidth()
                .padding(dimens.normalPadding),
            enabled = taskIsValid()
        ) {
            Text("Добавить задание", style = titleText)
        }
    }
}