package com.bashkir.documentstasks.ui.sreens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.compose.collectAsState
import com.bashkir.documentstasks.R
import com.bashkir.documentstasks.data.models.Task
import com.bashkir.documentstasks.ui.components.ErrorView
import com.bashkir.documentstasks.ui.components.LoadingScreen
import com.bashkir.documentstasks.ui.components.ShowPerformersView
import com.bashkir.documentstasks.ui.components.dialogs.CompleteTaskDialog
import com.bashkir.documentstasks.ui.components.topbars.TopBar
import com.bashkir.documentstasks.ui.theme.DocumentsTasksTheme.dimens
import com.bashkir.documentstasks.ui.theme.titleText
import com.bashkir.documentstasks.viewmodels.TasksState
import com.bashkir.documentstasks.viewmodels.TasksViewModel

@Composable
fun TaskDetailScreenBody(taskId: Int, navController: NavController, viewModel: TasksViewModel) {
    val tasks by viewModel.collectAsState(TasksState::tasks)
    var title by remember { mutableStateOf("Задача") }

    Scaffold(
        topBar = { TopBar(titleText = title, navController = navController) }
    ) {

        when (tasks) {
            is Success -> {
                val task = tasks()!![taskId]
                title = task.title
                TaskDetailView(task = task)
            }
            is Loading -> LoadingScreen()
            is Fail -> ErrorView("Не удалось загрузить задачи", tasks as Fail<*>)
            else -> ErrorView("Не удалось загрузить задачи")
        }

    }
}

@Composable
private fun TaskDetailView(task: Task) =
    Column(
        Modifier
            .padding(dimens.normalPadding)
            .verticalScroll(rememberScrollState())
    ) {
        val openDialog = remember { mutableStateOf(false) }

        Text(task.desc)
        Spacer(modifier = Modifier.height(dimens.normalPadding))
        Text(stringResource(R.string.performers), style = titleText)
        Spacer(modifier = Modifier.height(dimens.articlePadding))
        task.performs.ShowPerformersView()
        OutlinedButton(
            modifier = Modifier
                .padding(dimens.normalPadding)
                .align(CenterHorizontally),
            onClick = { openDialog.value = true }) {
            Text("Сдать задачу")
        }
        CompleteTaskDialog(openDialogState = openDialog, task = task)
    }