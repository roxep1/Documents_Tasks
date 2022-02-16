package com.bashkir.documentstasks.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.bashkir.documentstasks.data.models.Perform
import com.bashkir.documentstasks.data.models.PerformStatus
import com.bashkir.documentstasks.data.models.User
import com.bashkir.documentstasks.ui.theme.DocumentsTasksTheme

@Composable
fun List<Perform>.ShowPerformersView() =
    forEach { perform ->
        Row(
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
    }
