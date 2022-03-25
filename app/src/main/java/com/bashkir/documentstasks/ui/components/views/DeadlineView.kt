package com.bashkir.documentstasks.ui.components.views

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bashkir.documentstasks.ui.components.Label
import com.bashkir.documentstasks.ui.components.buttons.StyledTextButton
import com.bashkir.documentstasks.ui.theme.DocumentsTasksTheme
import com.bashkir.documentstasks.utils.formatToString
import com.vanpra.composematerialdialogs.MaterialDialogState
import java.time.LocalDateTime

@Composable
fun DeadlineView(
    dateDialogState: MaterialDialogState,
    timeDialogState: MaterialDialogState,
    taskDeadLine: LocalDateTime
) {
    Label("Срок сдачи:")
    Row(
        modifier = Modifier.padding(bottom = DocumentsTasksTheme.dimens.normalPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        StyledTextButton(
            label = "Дата: ",
            text = taskDeadLine.toLocalDate().formatToString(),
            onClick = dateDialogState::show
        )

        StyledTextButton(
            label = "Время: ",
            text = taskDeadLine.toLocalTime().formatToString(),
            onClick = timeDialogState::show
        )
    }
}