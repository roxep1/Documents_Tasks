package com.bashkir.documentstasks.ui.components.dialogs

import androidx.compose.runtime.Composable
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.title
import java.security.cert.CertPathValidator
import java.time.LocalTime

@Composable
fun TimePickerDialog(
    dialogState: MaterialDialogState,
    title: String = "",
    onBackClick: () -> Unit = {},
    onTimeSet: (LocalTime) -> Unit
) =
    MaterialDialog(
        dialogState = dialogState,
        buttons = {
            positiveButton("OK")
            negativeButton("Отмена", onClick = onBackClick)
        }
    ) {
        title(title)
        timepicker(onTimeChange = onTimeSet, is24HourClock = true)
    }