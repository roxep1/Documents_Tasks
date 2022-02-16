package com.bashkir.documentstasks.ui.sreens.bottom

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import com.airbnb.mvrx.compose.collectAsState
import com.bashkir.documentstasks.R
import com.bashkir.documentstasks.ui.components.AsyncView
import com.bashkir.documentstasks.ui.components.anim.AnimateVertical
import com.bashkir.documentstasks.ui.components.cards.TaskCardList
import com.bashkir.documentstasks.ui.components.cards.TaskFilterOption
import com.bashkir.documentstasks.ui.components.cards.TaskFilterSettingsCard
import com.bashkir.documentstasks.ui.components.topbars.TopBarBottomNav
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
        topBar = {
            TopBarBottomNav(
                navController = navController,
                searchTextFieldValue = searchTextField,
                actions = {
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
            )
        }) {
        AsyncView(tasks, "Не удалось загрузить задачи") {
            TaskCardList(
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