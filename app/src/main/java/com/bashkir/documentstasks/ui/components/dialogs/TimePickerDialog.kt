package com.bashkir.documentstasks.ui.components.dialogs

import androidx.compose.runtime.Composable
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import java.time.LocalTime

@Composable
fun TimePickerDialog(dialogState: MaterialDialogState, onTimeSet: (LocalTime) -> Unit) =
    MaterialDialog(
        dialogState = dialogState,
        buttons = {
            positiveButton("OK")
            negativeButton("Отмена")
        }
    ) {
        timepicker {
            onTimeSet(it)
        }
    }