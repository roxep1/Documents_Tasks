package com.bashkir.documentstasks.ui.components.views

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.bashkir.documentstasks.data.models.Agreement
import com.bashkir.documentstasks.ui.theme.DocumentsTasksTheme

@Composable
fun List<Agreement>.AgreementsView() = forEach {agreement ->
    AgreementInfo(agreement)
}

@Composable
private fun AgreementInfo(agreement: Agreement) = Row(
    modifier = Modifier
        .padding(top = DocumentsTasksTheme.dimens.normalPadding)
        .fillMaxWidth()
) {
    Text(
        agreement.user.fullName,
        Modifier
            .weight(1f)
            .align(Alignment.CenterVertically)
    )
    Text(
        agreement.user.email,
        Modifier
            .weight(1f)
            .align(Alignment.CenterVertically),
        textAlign = TextAlign.Center
    )
    Text(
        agreement.status.text,
        Modifier
            .weight(1f)
            .align(Alignment.CenterVertically),
        textAlign = TextAlign.Center,
        style = agreement.status.textStyle
    )
}
