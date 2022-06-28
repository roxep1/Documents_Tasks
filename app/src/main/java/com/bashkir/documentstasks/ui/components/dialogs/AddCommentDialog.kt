package com.bashkir.documentstasks.ui.components.dialogs

import androidx.compose.runtime.*
import androidx.compose.ui.focus.FocusRequester
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.input


@Composable
fun AddCommentDialog(dialogState: MaterialDialogState, setComment: (String) -> Unit) {
    var input by remember { mutableStateOf("") }

    MaterialDialog(dialogState = dialogState, buttons = {
        positiveButton("Отправить") { setComment(input) }
        negativeButton("Отмена")
    }) {

        input(
            label = "Комментарий",
            placeholder = "Комментарий к выполнению работы...",
            waitForPositiveButton = false,
            onInput = { input = it },
            focusOnShow = true
        )
        PositiveButtonEnabled(valid = input.isNotBlank()) {}
    }
}