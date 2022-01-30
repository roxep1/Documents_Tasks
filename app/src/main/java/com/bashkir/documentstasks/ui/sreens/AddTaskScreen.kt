package com.bashkir.documentstasks.ui.sreens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bashkir.documentstasks.data.models.User
import com.bashkir.documentstasks.data.test.testUserList1
import com.bashkir.documentstasks.ui.components.cards.UserCard
import com.bashkir.documentstasks.ui.components.dialogs.DatePickerDialog
import com.bashkir.documentstasks.ui.components.dialogs.TimePickerDialog
import com.bashkir.documentstasks.ui.components.topbars.TopBar
import com.bashkir.documentstasks.ui.theme.DocumentsTasksTheme
import com.bashkir.documentstasks.ui.theme.DocumentsTasksTheme.dimens
import com.bashkir.documentstasks.ui.theme.graySmallText
import com.bashkir.documentstasks.ui.theme.placeHolderText
import com.bashkir.documentstasks.ui.theme.titleText
import com.bashkir.documentstasks.utils.formatToString
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.customView
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDateTime
import java.time.LocalTime

@Composable
fun AddTaskScreenBody(navController: NavController) = Scaffold(topBar = {
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
            value = taskDesc,
            onValueChange = { taskDesc = it },
            modifier = Modifier.padding(bottom = dimens.normalPadding),
            placeholder = { Text("Описание", style = placeHolderText) }

        )

        DeadLineView(
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

        PerformerAddDialog(usersDialogState, taskPerformers)

        PerformersList(performers = taskPerformers) {
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
                //TODO
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

@Composable
private fun Label(label: String) = Text(label, style = graySmallText)

@Composable
private fun DeadLineView(
    dateDialogState: MaterialDialogState,
    timeDialogState: MaterialDialogState,
    taskDeadLine: LocalDateTime
) {
    Label("Срок сдачи:")
    Row(
        modifier = Modifier.padding(bottom = dimens.normalPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextButton(
            label = "Дата: ",
            materialDialogState = dateDialogState,
            text = taskDeadLine.toLocalDate().formatToString()
        )

        TextButton(
            label = "Время: ",
            materialDialogState = timeDialogState,
            text = taskDeadLine.toLocalTime().formatToString()
        )
    }
}

@Composable
private fun TextButton(label: String, materialDialogState: MaterialDialogState, text: String) {
    Text(label, style = titleText)
    TextButton(
        modifier = Modifier.padding(end = dimens.normalPadding),
        onClick = { materialDialogState.show() }
    ) {
        Text(text = text, style = titleText)
    }
}

@Composable
private fun PerformersList(
    performers: List<User>,
    addPerformerBtn: @Composable LazyItemScope.() -> Unit
) = Column {
    Label("Исполнители: ")
    LazyColumn(
        Modifier
            .fillMaxWidth()
            .requiredHeight(dimens.maxListHeight)
            .padding(top = dimens.articlePadding)
    ) {
        items(performers) { user ->
            UserCard(
                Modifier.padding(top = dimens.articlePadding),
                user = user
            )
        }
        item(content = addPerformerBtn)
    }
}

@Composable
private fun PerformerAddDialog(
    dialogState: MaterialDialogState,
    performers: SnapshotStateList<User>
) =
    MaterialDialog(dialogState = dialogState, buttons = {
        negativeButton("Отмена")
    }) {
        customView {
            LazyColumn {
                items(testUserList1.filter { !performers.contains(it) }) { user ->
                    UserCard(
                        Modifier
                            .padding(top = dimens.articlePadding)
                            .padding(horizontal = dimens.normalPadding),
                        user = user
                    ) {
                        performers.add(user)
                        dialogState.hide()
                    }
                }
            }
        }
    }

private fun LocalDateTime.plus(time: LocalTime): LocalDateTime =
    this.plusSeconds(time.second.toLong()).plusMinutes(time.minute.toLong())
        .plusHours(time.hour.toLong())

@Preview
@Composable
private fun AddTaskScreenPreview() = DocumentsTasksTheme {
    AddTaskScreenBody(navController = rememberNavController())
}