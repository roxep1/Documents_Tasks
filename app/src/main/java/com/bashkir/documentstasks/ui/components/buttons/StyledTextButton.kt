package com.bashkir.documentstasks.ui.components.buttons

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bashkir.documentstasks.ui.theme.DocumentsTasksTheme
import com.bashkir.documentstasks.ui.theme.titleText

@Composable
fun StyledTextButton(modifier: Modifier = Modifier, text: String, label: String? = null, onClick: () -> Unit = {}) {
    label?.let { Text(label, style = titleText) }
    androidx.compose.material.TextButton(
        modifier = modifier.padding(end = DocumentsTasksTheme.dimens.normalPadding),
        onClick = onClick
    ) {
        Text(text = text, style = titleText)
    }
}