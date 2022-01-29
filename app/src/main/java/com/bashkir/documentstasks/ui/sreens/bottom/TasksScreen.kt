package com.bashkir.documentstasks.ui.sreens.bottom

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bashkir.documentstasks.R
import com.bashkir.documentstasks.data.models.Task
import com.bashkir.documentstasks.data.test.testTasksList1
import com.bashkir.documentstasks.ui.components.AnimateVertical
import com.bashkir.documentstasks.ui.components.TaskCardList
import com.bashkir.documentstasks.ui.components.TopBarBottomNav
import com.bashkir.documentstasks.ui.components.cards.TaskFilterOption
import com.bashkir.documentstasks.ui.components.cards.TaskFilterSettingsCard
import com.bashkir.documentstasks.ui.theme.DocumentsTasksTheme
import com.bashkir.documentstasks.ui.theme.DocumentsTasksTheme.dimens
import com.bashkir.documentstasks.utils.navigate


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TasksScreenBody(navController: NavController) {
    val searchTextField = remember { mutableStateOf(TextFieldValue()) }
    val filterSettingsVisible = remember { mutableStateOf(false) }
    val taskFilterOption = remember { mutableStateOf(TaskFilterOption.ALL) }

    Scaffold(topBar = {
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
        TaskCardList(
            onClick = {
                navController.navigate(it)
            },
            tasks = filterTasks(
                testTasksList1,
                searchTextField.value.text,
                taskFilterOption.value
            )
        )

        AnimateVertical(visible = filterSettingsVisible) {
            TaskFilterSettingsCard(taskFilterOption)
        }

        AddTaskFloatingButton(Modifier.padding(dimens.normalPadding)) {
            //TODO
        }
    }
}

@Composable
private fun AddTaskFloatingButton(modifier: Modifier, onClick: () -> Unit) =
    ExtendedFloatingActionButton(
        modifier = modifier,
        text = { Text("Новая задача") },
        onClick = onClick,
        icon = { Icon(Icons.Default.Add, contentDescription = "add task button") })

private fun filterTasks(
    tasks: List<Task>,
    searchText: String,
    filterOption: TaskFilterOption
): List<Task> =
    //TODO
    tasks.filter { task ->
        if (searchText.isNotBlank() && searchText.isNotEmpty())
            task.title.contains(searchText) ||
                    task.desc.contains(searchText)
        else true
    }

@ExperimentalAnimationApi
@Preview
@Composable
private fun TasksScreenBodyPreview() = DocumentsTasksTheme {
    TasksScreenBody(navController = rememberNavController())
}