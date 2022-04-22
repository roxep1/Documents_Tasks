package com.bashkir.documentstasks.ui.components.buttons

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun RowScope.DeleteButton(modifier: Modifier = Modifier, onClick: () -> Unit) =
    IconButton(onClick = onClick, modifier.align(Alignment.CenterVertically)) {
        Icon(Icons.Default.Clear, "Delete button")
    }
