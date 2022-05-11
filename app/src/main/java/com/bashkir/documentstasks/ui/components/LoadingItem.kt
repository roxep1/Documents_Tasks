package com.bashkir.documentstasks.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.ui.Modifier
import com.bashkir.documentstasks.ui.theme.DocumentsTasksTheme

fun LazyListScope.loadingItem(isLoading: Boolean) = item {
    if (isLoading)
        Row(
            Modifier
                .fillMaxWidth()
                .padding(DocumentsTasksTheme.dimens.normalPadding),
            horizontalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
        }
}