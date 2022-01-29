package com.bashkir.documentstasks.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.bashkir.documentstasks.data.models.Status
import com.bashkir.documentstasks.data.models.User
import com.bashkir.documentstasks.ui.theme.DocumentsTasksTheme

@Composable
fun Map<User, Status>.ShowPerformers() =
    this.forEach { (user, status) ->
        Row(
            modifier = Modifier
                .padding(top = DocumentsTasksTheme.dimens.normalPadding)
                .fillMaxWidth()
        ) {
            Text(
                user.name.fullName,
                Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            )
            Text(
                user.email,
                Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically),
                textAlign = TextAlign.Center
            )
            Text(
                status.text,
                Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically),
                textAlign = TextAlign.Center,
                style = status.textStyle
            )
        }
    }
