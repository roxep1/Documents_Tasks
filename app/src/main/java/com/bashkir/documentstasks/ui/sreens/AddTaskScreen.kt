package com.bashkir.documentstasks.ui.sreens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import com.airbnb.mvrx.compose.collectAsState
import com.bashkir.documentstasks.contracts.DocumentSelectContract
import com.bashkir.documentstasks.data.models.*
import com.bashkir.documentstasks.ui.components.cards.FilesList
import com.bashkir.documentstasks.ui.components.cards.UsersList
import com.bashkir.documentstasks.ui.components.dialogs.AddUserDialog
import com.bashkir.documentstasks.ui.components.dialogs.DatePickerDialog
import com.bashkir.documentstasks.ui.components.dialogs.TimePickerDialog
import com.bashkir.documentstasks.ui.components.topbars.TopBar
import com.bashkir.documentstasks.ui.components.views.DeadlineView
import com.bashkir.documentstasks.ui.theme.DocumentsTasksTheme.dimens
import com.bashkir.documentstasks.ui.theme.placeHolderText
import com.bashkir.documentstasks.ui.theme.titleText
import com.bashkir.documentstasks.utils.getExtension
import com.bashkir.documentstasks.utils.withoutExtension
import com.bashkir.documentstasks.viewmodels.TasksState
import com.bashkir.documentstasks.viewmodels.TasksViewModel
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDateTime

@OptIn(ExperimentalFoundationApi::class)
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
    val taskDocuments = remember { mutableStateListOf<DocumentForm>() }
    val taskFiles = remember { mutableStateListOf<FileForm>() }

    val dateDialogState = rememberMaterialDialogState()
    val timeDialogState = rememberMaterialDialogState()
    val usersDialogState = rememberMaterialDialogState()

    val users by viewModel.collectAsState(TasksState::users)

    fun taskIsValid(): Boolean = taskDeadLine.isAfter(LocalDateTime.now()) &&
            taskTitle.text.isNotBlank() && taskTitle.text.isNotEmpty()
            && taskDesc.text.isNotEmpty() && taskDesc.text.isNotBlank()
            && taskPerformers.isNotEmpty()

    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(dimens.normalPadding)
    ) {
        item {
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
                taskDeadLine = taskDeadLine.with(it)
            }

            TimePickerDialog(dialogState = timeDialogState) {
                taskDeadLine = taskDeadLine.with(it)
            }

            AddUserDialog(usersDialogState, taskPerformers, users, viewModel::getAllUsers)

            UsersList(
                users = taskPerformers,
                deleteUserOnClick = taskPerformers::remove,
                label = "Исполнители"
            ) {
                OutlinedButton(onClick = usersDialogState::show) {
                    Text("Добавить исполнителя")
                }
            }

            val openDocLauncher =
                rememberLauncherForActivityResult(
                    contract = DocumentSelectContract(),
                    onResult = {
                        viewModel.getDataOfFile(it) { displayName, size, bytes ->
                            if (displayName != null && size != null && bytes != null) {
                                taskDocuments.add(
                                    DocumentForm(
                                        displayName,
                                        null,
                                        listOf(),
                                        listOf()
                                    )
                                )
                                taskFiles.add(
                                    FileForm(
                                        displayName.withoutExtension(),
                                        size,
                                        bytes,
                                        displayName.getExtension()
                                    )
                                )
                            }
                        }
                    })

            FilesList(
                taskFiles,
                onDelete = {
                    taskFiles.indexOf(it).let { index ->
                        taskFiles.removeAt(index)
                        taskDocuments.removeAt(index)
                    }
                }) {
                OutlinedButton(onClick = { openDocLauncher.launch(0) }) {
                    Text("Добавить документ")
                }
            }
        }
        stickyHeader {
            Button(
                onClick = {
                    viewModel.addTask(
                        TaskForm(
                            taskTitle.text,
                            taskDesc.text,
                            taskDeadLine,
                            taskPerformers.toForms().map { PerformForm(it) },
                            taskDocuments
                        ),
                        taskFiles
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
}