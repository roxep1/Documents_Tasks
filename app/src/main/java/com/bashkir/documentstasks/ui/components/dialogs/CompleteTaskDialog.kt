package com.bashkir.documentstasks.ui.components.dialogs

import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.bashkir.documentstasks.R
import com.bashkir.documentstasks.data.models.Task
import com.bashkir.documentstasks.ui.theme.DocumentsTasksTheme.dimens


@Composable
fun CompleteTaskDialog(
    openDialogState: MutableState<Boolean>,
    task: Task,
    onCompleteClick: (Task) -> Unit
) {
    if (openDialogState.value)
        AlertDialog(
            onDismissRequest = { openDialogState.value = false },
            title = { Text(stringResource(R.string.complete_task_dialog_title)) },
            text = { Text("Вы уверены, что хотите сдать задачу ${task.title}?") },
            buttons = {
                Button(
                    modifier = Modifier.padding(
                        top = dimens.normalPadding,
                        bottom = dimens.normalPadding
                    ),
                    onClick = {
                        onCompleteClick(task)
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