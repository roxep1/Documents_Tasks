package com.bashkir.documentstasks.ui.components.dialogs

import androidx.compose.runtime.Composable
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.input


@Composable
fun AddCommentDialog(dialogState: MaterialDialogState, setComment: (String) -> Unit) =
    MaterialDialog(dialogState = dialogState, buttons = {
        positiveButton("Отправить")
        negativeButton("Отмена")
    }) {
        input(
            label = "Комментарий",
            hint = "Комментарий к выполнению работы...",
            onInput = setComment,
            isTextValid = {it.isNotBlank()}
        )
    }