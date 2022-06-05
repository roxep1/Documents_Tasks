package com.bashkir.documentstasks.ui.components.cards

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.bashkir.documentstasks.data.models.Document
import com.bashkir.documentstasks.data.models.File
import com.bashkir.documentstasks.data.models.FileForm
import com.bashkir.documentstasks.ui.theme.DocumentsTasksTheme.dimens
import com.bashkir.documentstasks.ui.theme.cardShape
import com.bashkir.documentstasks.ui.theme.normalText
import com.bashkir.documentstasks.utils.navigate
import com.bashkir.documentstasks.utils.toStringMB

@Composable
fun FileCard(displayName: String?, size: Long? = null, onClick: (() -> Unit)? = null) =
    Card(
        Modifier
            .fillMaxWidth()
            .clickable(onClick != null, onClick = onClick ?: {})
            .padding(dimens.articlePadding),
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
            size?.let {
                Text(
                    size.toStringMB(), style = normalText
                )
            }
        }
    }

@Composable
fun FilesList(
    documents: List<Document>,
    navController: NavController
) = Column {
    documents.forEach { document ->
        FileCard(displayName = document.title) { navController.navigate(document) }
    }
}

@Composable
fun FileCard(
    displayName: String,
    size: Float,
    onDelete: () -> Unit
) =
    Card(
        Modifier
            .fillMaxWidth()
            .padding(dimens.articlePadding),
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
            Text(displayName, style = normalText)
            Text(
                size.toStringMB(), style = normalText
            )
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, null)
            }
        }
    }

@Composable
fun FilesList(
    files: List<FileForm>,
    onDelete: (FileForm) -> Unit,
    lastItem: @Composable () -> Unit
) = Column(
    Modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally
) {
    files.forEach { file ->
        FileCard(file.name, file.size!!) { onDelete(file) }
    }
    lastItem()
}
