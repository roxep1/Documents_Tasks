package com.bashkir.documentstasks.ui.components.dialogs

import androidx.compose.runtime.Composable
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import java.time.LocalDate

@Composable
fun DatePickerDialog(dialogState: MaterialDialogState, onDateSet: (LocalDate) -> Unit) =
    MaterialDialog(
        dialogState = dialogState,
        buttons = {
            positiveButton("ОК")
            negativeButton("Отмена")
        }
    ) {
        datepicker {
            onDateSet(it)
        }
    }