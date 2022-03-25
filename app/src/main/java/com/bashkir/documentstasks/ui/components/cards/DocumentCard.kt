package com.bashkir.documentstasks.ui.components.cards

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bashkir.documentstasks.data.models.Agreement
import com.bashkir.documentstasks.data.models.Document
import com.bashkir.documentstasks.data.models.Documentable
import com.bashkir.documentstasks.data.models.Familiarize
import com.bashkir.documentstasks.ui.components.views.AgreementsView

@Composable
fun DocumentCardList(
    modifier: Modifier = Modifier,
    documents: List<Documentable>,
    onDetailsClick: (Document) -> Unit
) = LazyColumn(modifier = modifier.fillMaxSize()) {
    items(documents.toList()) { document ->
        DocumentCard(document) { onDetailsClick(document.toDocument()) }
    }
}

@Composable
fun DocumentCard(
    document: Documentable,
    onDetailsClick: () -> Unit
) =
    document.toDocument().let {
        ExpandingCard(
            title = it.title,
            desc = it.desc,
            author = it.author,
            pubDate = it.created,
            expandingButtonText = if (document is Agreement) "Согласования: " else "Подробнее",
            mainInfo = {
                MainInfo {
                    if (document is Agreement)
                        Text("Необходимо согласовать до ${document.deadline}")
                    else if (document is Familiarize)
                        Text("Ознакомиться")
                }
            },
            expandedInfo = {
                if (document is Document)
                    document.agreement.AgreementsView()
            },
            onDetailsClick = onDetailsClick
        )
    }