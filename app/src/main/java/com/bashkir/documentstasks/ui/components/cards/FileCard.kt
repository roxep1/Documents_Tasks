package com.bashkir.documentstasks.ui.components.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bashkir.documentstasks.ui.theme.DocumentsTasksTheme.dimens
import com.bashkir.documentstasks.ui.theme.cardShape
import com.bashkir.documentstasks.ui.theme.normalText
import com.bashkir.documentstasks.utils.toMB

@Composable
fun FileCard(displayName: String?, size: Long?) =
    Card(
        Modifier
            .fillMaxWidth()
            .padding(dimens.normalPadding),
        elevation = dimens.normalElevation,
        shape = cardShape
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(dimens.normalPadding),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(Icons.Default.List, null)
            Text(displayName ?: "Файл", style = normalText)
            Text(
                size?.toMB() ?: "Неизвестно", style = normalText
            )
        }
    }
