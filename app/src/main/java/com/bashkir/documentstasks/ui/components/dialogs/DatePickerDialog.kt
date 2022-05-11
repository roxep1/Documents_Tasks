package com.bashkir.documentstasks.ui.components.dialogs

import androidx.compose.runtime.Composable
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.title
import java.time.LocalDate

@Composable
fun DatePickerDialog(
    dialogState: MaterialDialogState,
    title: String = "",
    validator: (LocalDate) -> Boolean = { true },
    onBackClick: () -> Unit = {},
    onDateSet: (LocalDate) -> Unit
) =
    MaterialDialog(
        dialogState = dialogState,
        buttons = {
            positiveButton("ОК")
            negativeButton("Отмена", onClick = onBackClick)
        }
    ) {
        title(title)
        datepicker(onDateChange = onDateSet, allowedDateValidator = validator)
    }