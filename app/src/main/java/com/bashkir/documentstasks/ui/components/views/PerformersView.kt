package com.bashkir.documentstasks.ui.components.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.bashkir.documentstasks.data.models.Document
import com.bashkir.documentstasks.data.models.Perform
import com.bashkir.documentstasks.ui.components.Label
import com.bashkir.documentstasks.ui.theme.DocumentsTasksTheme

@Composable
fun List<Perform>.PerformersView(extended: Boolean = false) =
    forEach { perform ->
        Column {
            PerformerInfo(perform)
            if (extended) {
                CheckForAdditionalInfo(
                    perform,
                    comment = {
                        Label("Комментарий исполнителя:")
                        Text(it)
                    },
                    documents = { Label("Файлы исполнителя:")/*TODO */ }
                )
            } else {
                CheckForAdditionalInfo(perform, comment = { Label("Имеется комментарий к выполнению") }) {
                    Label("Имеются прикрепленные документы")
                }
            }
        }
    }

@Composable
fun CheckForAdditionalInfo(
    perform: Perform,
    comment: @Composable (String) -> Unit,
    documents: @Composable (List<Document>) -> Unit
) {
    perform.comment?.let {
        comment(it)
    }
    if (perform.documents.isNotEmpty()) documents(perform.documents)
}

@Composable
private fun PerformerInfo(perform: Perform) = Row(
    modifier = Modifier
        .padding(top = DocumentsTasksTheme.dimens.normalPadding)
        .fillMaxWidth()
) {
    Text(
        perform.user.fullName,
        Modifier
            .weight(1f)
            .align(Alignment.CenterVertically)
    )
    Text(
        perform.user.email,
        Modifier
            .weight(1f)
            .align(Alignment.CenterVertically),
        textAlign = TextAlign.Center
    )
    Text(
        perform.status.text,
        Modifier
            .weight(1f)
            .align(Alignment.CenterVertically),
        textAlign = TextAlign.Center,
        style = perform.status.textStyle
    )
}
