package com.bashkir.documentstasks.ui.sreens.bottom

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import com.airbnb.mvrx.compose.collectAsState
import com.bashkir.documentstasks.ui.components.AsyncView
import com.bashkir.documentstasks.ui.components.TasksFloatingButton
import com.bashkir.documentstasks.ui.components.anim.AnimateVertical
import com.bashkir.documentstasks.ui.components.cards.TaskCardList
import com.bashkir.documentstasks.ui.components.cards.TaskFilterOption
import com.bashkir.documentstasks.ui.components.cards.TaskFilterSettingsCard
import com.bashkir.documentstasks.ui.components.topbars.TopBarTasks
import com.bashkir.documentstasks.ui.theme.DocumentsTasksTheme.dimens
import com.bashkir.documentstasks.utils.navigate
import com.bashkir.documentstasks.viewmodels.TasksState
import com.bashkir.documentstasks.viewmodels.TasksViewModel


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TasksScreenBody(
    navController: NavController,
    viewModel: TasksViewModel
) {
    OnCreate(viewModel = viewModel)

    val searchTextField = remember { mutableStateOf(TextFieldValue()) }
    val filterSettingsVisible = remember { mutableStateOf(false) }
    val taskFilterOption = remember { mutableStateOf(TaskFilterOption.ALL) }
    val tasks by viewModel.collectAsState(TasksState::tasks)

    Scaffold(
        topBar = { TopBarTasks(navController, searchTextField, filterSettingsVisible) },
        floatingActionButton = { TasksFloatingButton(navController) })
    {
        AsyncView(tasks, "Не удалось загрузить задачи") {
            TaskCardList(
                modifier = Modifier.padding(bottom = dimens.normalPadding),
                onClick = { task ->
                    navController.navigate(task)
                },
                tasks = viewModel.filterTasks(
                    it,
                    searchTextField.value.text,
                    taskFilterOption.value
                )
            )
        }

        AnimateVertical(visible = filterSettingsVisible) {
            TaskFilterSettingsCard(taskFilterOption)
        }
    }
}

@Composable
private fun OnCreate(viewModel: TasksViewModel) = LaunchedEffect(viewModel) {
    viewModel.getAllTasks()
    viewModel.getAllUsers()
}