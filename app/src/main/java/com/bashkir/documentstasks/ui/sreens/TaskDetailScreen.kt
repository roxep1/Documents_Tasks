package com.bashkir.documentstasks.ui.sreens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.bashkir.documentstasks.R
import com.bashkir.documentstasks.data.models.Task
import com.bashkir.documentstasks.ui.components.CompleteTaskDialog
import com.bashkir.documentstasks.ui.components.ShowPerformers
import com.bashkir.documentstasks.ui.components.TopBar
import com.bashkir.documentstasks.ui.theme.DocumentsTasksTheme.dimens
import com.bashkir.documentstasks.ui.theme.titleText

@Composable
fun TaskDetailScreenBody(task: Task, navController: NavController) = Scaffold(
    topBar = { TopBar(titleText = task.title, navController = navController) }
) {
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
        task.performers.ShowPerformers()
        OutlinedButton(
            modifier = Modifier
                .padding(dimens.normalPadding)
                .align(CenterHorizontally),
            onClick = {openDialog.value = true}) {
            Text("Сдать задачу")
        }
        CompleteTaskDialog(openDialogState = openDialog, task = task)
    }
}