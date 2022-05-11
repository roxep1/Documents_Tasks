package com.bashkir.documentstasks.ui.components.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.bashkir.documentstasks.ui.components.buttons.StyledTextButton
import com.bashkir.documentstasks.ui.theme.DocumentsTasksTheme

@Composable
fun NoElementsView(onUpdate: () -> Unit) =
    Box(Modifier.fillMaxSize()) {
        Column(
            Modifier
                .align(Alignment.Center)
                .fillMaxWidth()) {
            Text(
                "В этом списке пока пусто :(",
                fontSize = DocumentsTasksTheme.dimens.titleText,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(CenterHorizontally)
            )
            StyledTextButton(
                text = "Обновить",
                onClick = onUpdate,
                modifier = Modifier
                    .align(CenterHorizontally)
            )
        }
    }