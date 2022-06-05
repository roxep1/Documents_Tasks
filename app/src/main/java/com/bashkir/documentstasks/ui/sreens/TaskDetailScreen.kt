package com.bashkir.documentstasks.ui.sreens

import androidx.activity.compose.rememberLauncherForActivityResult
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
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.airbnb.mvrx.compose.collectAsState
import com.bashkir.documentstasks.R
import com.bashkir.documentstasks.contracts.DocumentSelectContract
import com.bashkir.documentstasks.data.models.PerformStatus.*
import com.bashkir.documentstasks.data.models.Task
import com.bashkir.documentstasks.ui.components.Label
import com.bashkir.documentstasks.ui.components.cards.FilesList
import com.bashkir.documentstasks.ui.components.dialogs.AddCommentDialog
import com.bashkir.documentstasks.ui.components.dialogs.CompleteTaskDialog
import com.bashkir.documentstasks.ui.components.topbars.TopBar
import com.bashkir.documentstasks.ui.components.views.AsyncLoadingView
import com.bashkir.documentstasks.ui.components.views.AsyncView
import com.bashkir.documentstasks.ui.components.views.PerformersView
import com.bashkir.documentstasks.ui.theme.DocumentsTasksTheme.dimens
import com.bashkir.documentstasks.ui.theme.titleText
import com.bashkir.documentstasks.utils.formatToString
import com.bashkir.documentstasks.viewmodels.TasksState
import com.bashkir.documentstasks.viewmodels.TasksViewModel
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.message
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import com.vanpra.composematerialdialogs.title

@Composable
fun TaskDetailScreenBody(taskId: Int, navController: NavController, viewModel: TasksViewModel) {
    val tasks by viewModel.collectAsState(TasksState::tasks)
    var title by remember { mutableStateOf("Задача") }
    val loadingState by viewModel.collectAsState { it.loadingState }

    AsyncLoadingView(loadingState) {
        Scaffold(
            topBar = { TopBar(titleText = title, navController = navController) }
        ) {
            AsyncView(
                tasks,
                "Не удалось загрузить задачи",
                viewModel::getAllTasks
            ) { loadedTasks, _ ->
                loadedTasks.find { task -> task.id == taskId }?.let { task ->
                    title = task.title
                    TaskDetailView(task = task, viewModel, navController)
                }
            }
        }
    }
}

@Composable
private fun TaskDetailView(
    task: Task,
    viewModel: TasksViewModel,
    navController: NavController
) =
    Column(
        Modifier
            .padding(dimens.normalPadding)
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
    ) {

        Text(task.desc)
        Spacer(modifier = Modifier.height(dimens.normalPadding))
        Text(
            "Срок сдачи: ${task.deadline.formatToString()}",
            fontWeight = FontWeight.Bold,
            fontSize = dimens.titleText
        )
        FilesList(documents = task.documents, navController = navController)
        if (task.author.id == viewModel.myId)
            IssuedTaskView(task, viewModel, navController)
        else
            MyTaskView(task, viewModel, navController)
    }

@Composable
private fun ColumnScope.MyTaskView(
    task: Task,
    viewModel: TasksViewModel,
    navController: NavController
) {
    viewModel.getMyPerform(task).let { perform ->

        if (perform.status != Completed) {
            val openDialog = remember { mutableStateOf(false) }

            OutlinedButton(
                modifier = Modifier
                    .padding(top = dimens.normalPadding)
                    .align(CenterHorizontally),
                onClick = {
                    if (perform.status == InProgress) openDialog.value = true
                    else viewModel.inProgressTask(task)
                }) {
                Text(if (perform.status == InProgress) "Сдать задачу" else "Приступить")
            }

            CompleteTaskDialog(
                openDialogState = openDialog,
                task = task,
                onCompleteClick = viewModel::completeTask
            )
        }

        if (perform.documents.isNotEmpty())
            FilesList(
                documents = perform.documents,
                navController
            )

        if (perform.status != Waiting) {
            val dialogState = rememberMaterialDialogState()
            val selectDocLauncher = rememberLauncherForActivityResult(
                contract = DocumentSelectContract(),
                onResult = { viewModel.addDocumentToTask(task, it) }
            )

            TaskDetailButton("Прикрепить файл к задаче") { selectDocLauncher.launch(0) }
            TaskDetailButton("Оставить комментарий к выполнению", onClick = dialogState::show)

            if (perform.comment != null) {
                Label(label = "Вы оставили комментарий к выполнению:")
                Text(perform.comment)
            }
            AddCommentDialog(dialogState) { comment ->
                viewModel.addCommentToTask(task, comment)
            }
        }
    }
}

@Composable
private fun ColumnScope.IssuedTaskView(
    task: Task,
    viewModel: TasksViewModel,
    navController: NavController
) {
    val dialogState = rememberMaterialDialogState()

    Text(stringResource(R.string.performers), style = titleText)
    Spacer(modifier = Modifier.height(dimens.articlePadding))
    task.performs.PerformersView(navController)
    TaskDetailButton(text = "Удалить задачу", onClick = dialogState::show)

    MaterialDialog(dialogState, buttons = {
        positiveButton("Удалить") {
            viewModel.deleteTask(task)
            navController.popBackStack()
        }
        negativeButton("Отмена")
    }) {
        title("Внимание")
        message("Вы уверены, что хотите удалить задачу ${task.title}?")
    }
}

@Composable
private fun ColumnScope.TaskDetailButton(text: String, onClick: () -> Unit) = OutlinedButton(
    modifier = Modifier
        .padding(top = dimens.normalPadding)
        .align(CenterHorizontally),
    onClick = onClick
) {
    Text(text)
}