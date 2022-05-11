package com.bashkir.documentstasks.ui.sreens.bottom

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import com.airbnb.mvrx.compose.collectAsState
import com.bashkir.documentstasks.ui.components.anim.AnimateVertical
import com.bashkir.documentstasks.ui.components.buttons.AddFloatingButton
import com.bashkir.documentstasks.ui.components.cards.FilterSettingsCard
import com.bashkir.documentstasks.ui.components.cards.TaskCardList
import com.bashkir.documentstasks.ui.components.filters.TaskFilterOption
import com.bashkir.documentstasks.ui.components.topbars.ExtendedTopBarBottomNav
import com.bashkir.documentstasks.ui.components.views.AsyncView
import com.bashkir.documentstasks.ui.navigation.Screen
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
    val searchTextField = remember { mutableStateOf(TextFieldValue()) }
    val filterSettingsVisible = remember { mutableStateOf(false) }
    val taskFilterOption = remember { mutableStateOf(TaskFilterOption.ALL) }
    val tasks by viewModel.collectAsState(TasksState::tasks)

    Scaffold(
        topBar = { ExtendedTopBarBottomNav(navController, searchTextField, filterSettingsVisible) },
        floatingActionButton = {
            AddFloatingButton(
                navController,
                "Новое задание",
                Screen.AddTask.destination
            )
        })
    {
        AsyncView(
            tasks,
            "Не удалось загрузить задачи",
            onUpdate = viewModel::getAllTasks
        ) { loadedTasks, isLoading ->
            TaskCardList(
                modifier = Modifier.padding(bottom = dimens.normalPadding),
                onDetailsClick = navController::navigate,
                tasks = viewModel.filterTasks(
                    loadedTasks,
                    searchTextField.value.text,
                    taskFilterOption.value
                ),
                isLoading = isLoading,
                onUpdate = viewModel::getAllTasks,
                myId = viewModel.myId
            )
        }

        AnimateVertical(visible = filterSettingsVisible) {
            FilterSettingsCard(taskFilterOption) {
                filterSettingsVisible.value = false
            }
        }
    }
}