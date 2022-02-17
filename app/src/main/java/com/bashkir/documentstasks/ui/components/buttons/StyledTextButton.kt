package com.bashkir.documentstasks.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bashkir.documentstasks.ui.theme.DocumentsTasksTheme
import com.bashkir.documentstasks.ui.theme.titleText

@Composable
fun StyledTextButton(label: String, text: String, onClick: () -> Unit) {
    Text(label, style = titleText)
    androidx.compose.material.TextButton(
        modifier = Modifier.padding(end = DocumentsTasksTheme.dimens.normalPadding),
        onClick = onClick
    ) {
        Text(text = text, style = titleText)
    }
}