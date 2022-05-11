package com.bashkir.documentstasks.ui.components.cards

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bashkir.documentstasks.contracts.DocumentCreateContract
import com.bashkir.documentstasks.data.models.Document
import com.bashkir.documentstasks.ui.theme.DocumentsTasksTheme.dimens
import com.bashkir.documentstasks.ui.theme.cardShape
import com.bashkir.documentstasks.ui.theme.normalText
import com.bashkir.documentstasks.utils.toMB

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
                    size.toMB(), style = normalText
                )
            }
        }
    }

@Composable
fun FilesList(documents: List<Document>, onResult: (Uri?, Document) -> Unit) = Column {
    documents.forEach { document ->
        val createDocLauncher =
            rememberLauncherForActivityResult(
                contract = DocumentCreateContract(),
                onResult = { onResult(it, document) }
            )

        FileCard(displayName = document.title) {
            createDocLauncher.launch(document)
        }
    }
}
