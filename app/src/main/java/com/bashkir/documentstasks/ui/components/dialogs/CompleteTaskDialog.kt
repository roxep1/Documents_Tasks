package com.bashkir.documentstasks.ui.components.dialogs

import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.bashkir.documentstasks.R
import com.bashkir.documentstasks.data.models.Task
import com.bashkir.documentstasks.data.test.testTask1
import com.bashkir.documentstasks.ui.theme.DocumentsTasksTheme
import com.bashkir.documentstasks.ui.theme.DocumentsTasksTheme.dimens


@Composable
fun CompleteTaskDialog(openDialogState: MutableState<Boolean>, task: Task) {
    if (openDialogState.value)
        AlertDialog(
            onDismissRequest = { openDialogState.value = false },
            title = { Text(stringResource(R.string.complete_task_dialog_title)) },
            text = { Text("Вы уверены, что хотите сдать задачу ${task.title}?") },
            buttons = {
                OutlinedButton(
                    onClick = {
                        //TODO
                    }) {
                    Text("Прикрепить файл к задаче")
                }

                Button(
                    modifier = Modifier.padding(
                        top = dimens.normalPadding,
                        bottom = dimens.normalPadding
                    ),
                    onClick = {
                        //TODO
                        openDialogState.value = false
                    }) {
                    Text(stringResource(R.string.complete_task_dialog_title))
                }

                Button(
                    onClick = {
                        openDialogState.value = false
                    }
                ) {
                    Text("Отмена")
                }

            }
        )
}