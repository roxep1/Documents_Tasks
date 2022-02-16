package com.bashkir.documentstasks.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.airbnb.mvrx.Fail
import com.bashkir.documentstasks.ui.theme.DocumentsTasksTheme

@Composable
fun ErrorView(text: String, asyncFail: Fail<*>? = null) =
    Box(Modifier.fillMaxSize()) {
        Text(
            "$text\n${asyncFail?.error?.message ?: ""}",
            color = Color.Red,
            fontSize = DocumentsTasksTheme.dimens.titleText,
            modifier = Modifier.align(
                Alignment.Center
            )
        )
    }
