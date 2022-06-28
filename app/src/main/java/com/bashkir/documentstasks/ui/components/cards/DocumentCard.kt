package com.bashkir.documentstasks.ui.components.cards

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.bashkir.documentstasks.data.models.Agreement
import com.bashkir.documentstasks.data.models.Document
import com.bashkir.documentstasks.data.models.Documentable
import com.bashkir.documentstasks.data.models.Familiarize
import com.bashkir.documentstasks.ui.components.views.AgreementsView
import com.bashkir.documentstasks.utils.formatCutToString
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun DocumentCardList(
    modifier: Modifier = Modifier,
    documents: List<Documentable>,
    isLoading: Boolean,
    onDetailsClick: (Document) -> Unit,
    onUpdate: () -> Unit
) = SwipeRefresh(
    state = rememberSwipeRefreshState(isRefreshing = isLoading),
    onRefresh = onUpdate
) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(documents.toList()) { document ->
            DocumentCard(document) { onDetailsClick(document.toDocument()) }
        }
    }
}

@Composable
fun DocumentCard(
    documentable: Documentable,
    onDetailsClick: () -> Unit
) = documentable.toDocument().let { document ->
    ExpandingCard(
        title = document.title,
        desc = document.desc,
        author = document.author.shortFullName,
        pubDate = document.created,
        expandingButtonText = "Подробнее",
        mainInfo = {
            MainInfo(Modifier.weight(if (document.desc == null || document.desc.isBlank()) 1F else 0.5f)) {
                if (documentable is Agreement)
                    Text(
                        "Cогласовать до: ${documentable.deadline.formatCutToString()}",
                        fontWeight = FontWeight.Bold
                    )
                else if (documentable is Familiarize)
                    Text(
                        if (!documentable.checked) "Ознакомиться" else "Вы уже ознакомлены",
                        fontWeight = FontWeight.Bold,
                        color = if (!documentable.checked) Color.Red else Color.Green
                    )
            }
        },
        expandedInfo = {
            if (documentable is Document)
                documentable.agreement.AgreementsView()
        },
        onDetailsClick = onDetailsClick
    )
}