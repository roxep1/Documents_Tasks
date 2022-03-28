package com.bashkir.documentstasks.ui.sreens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.airbnb.mvrx.compose.collectAsState
import com.bashkir.documentstasks.data.models.*
import com.bashkir.documentstasks.ui.components.buttons.StyledTextButton
import com.bashkir.documentstasks.ui.components.topbars.TopBar
import com.bashkir.documentstasks.ui.components.views.AgreementsView
import com.bashkir.documentstasks.ui.components.views.AsyncView
import com.bashkir.documentstasks.ui.theme.DocumentsTasksTheme.dimens
import com.bashkir.documentstasks.utils.formatToString
import com.bashkir.documentstasks.viewmodels.DocumentsViewModel
import com.vanpra.composematerialdialogs.*

@Composable
fun DocumentDetailScreenBody(
    documentId: Int,
    navController: NavController,
    viewModel: DocumentsViewModel
) {
    val documents by viewModel.collectAsState { it.documents }
    var title by remember { mutableStateOf("Документ") }

    Scaffold(
        topBar = { TopBar(title, navController) }
    ) {
        AsyncView(async = documents, errorText = "Неудалось загрузить документы") {
            it.find { document -> document.toDocument().id == documentId }?.let { document ->
                title = document.toDocument().title
                DocumentDetailView(document, viewModel)
            }
        }
    }
}

@Composable
private fun DocumentDetailView(document: Documentable, viewModel: DocumentsViewModel) =
    Column(
        Modifier
            .padding(dimens.normalPadding)
            .fillMaxSize()
    ) {
        document.toDocument().run {
            desc?.let {
                Text(desc)
                Spacer(Modifier.height(dimens.normalPadding))
            }
            templateId?.let {
                Text(templateId, fontWeight = FontWeight.Bold)
            }
        }
        StyledTextButton(
            Modifier.align(CenterHorizontally),
            "Скачать документ",
            onClick = { viewModel.downloadDocument(document.toDocument()) }
        )

        when (document) {
            is Agreement -> AgreementView(document, viewModel)
            is Document -> DocumentView(document, viewModel)
            is Familiarize -> FamiliarizeView(document, viewModel)
        }
    }

@Composable
private fun ColumnScope.FamiliarizeView(familiarize: Familiarize, viewModel: DocumentsViewModel) =
    if (!familiarize.checked) {
        Text("Необходимо ознакомиться")
        OutlinedButton(
            onClick = { viewModel.familiarizeDocument(familiarize) },
            Modifier.align(CenterHorizontally)
        ) {
            Text("Ознакомлен")
        }
    } else Text("Вы уже ознакомлены с данным документом")

@Composable
private fun ColumnScope.DocumentView(document: Document, viewModel: DocumentsViewModel) {
    OutlinedButton(onClick = viewModel::updateDocument, Modifier.align(CenterHorizontally)) {
        Text("Обновить документ")
    }
    document.agreement.AgreementsView()
    Spacer(Modifier.height(dimens.normalPadding))
    Text("На ознакомление отправлено:")
    LazyColumn(Modifier.align(CenterHorizontally)) {
        items(document.familiarize) { familiarize ->
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Text(familiarize.user.fullName)
                Text(if (familiarize.checked) "Ознакомлен" else "Не ознакомлен")
            }
        }
    }
}

@Composable
private fun ColumnScope.AgreementView(agreement: Agreement, viewModel: DocumentsViewModel) {
    val agreedDialogState = rememberMaterialDialogState()
    val declineDialogState = rememberMaterialDialogState()

    when (agreement.status) {
        AgreementStatus.Sent -> Text(
            "Необходимо принять решение о согласовании до: ${agreement.deadline.formatToString()}",
            fontWeight = FontWeight.Bold
        )
        else -> {
            Text("Вы уже приняли решение о согласовании: ", fontWeight = FontWeight.Bold)
            Spacer(Modifier.width(dimens.articlePadding))
            Text(agreement.status.text, style = agreement.status.textStyle)
        }
    }
    agreement.comment?.let {
        Spacer(modifier = Modifier.height(dimens.normalPadding))
        Text("Вы оставили комментарий к выполнению:\n${agreement.comment}")
    }
    Row{
        if (agreement.status != AgreementStatus.Agreed)
            OutlinedButton(
                onClick = agreedDialogState::show,
                Modifier.weight(1F)
            ) {
                Text("Согласовать")
            }

        if (agreement.status != AgreementStatus.Declined)
            OutlinedButton(
                onClick = declineDialogState::show,
                Modifier.weight(1F)
            ) {
                Text("Отказать в согласовании")
            }
    }


    AgreementStatusChangeDialog(dialogState = agreedDialogState) { comment ->
        if (comment != null && comment.isNotBlank())
            viewModel.agreedDocument(agreement, comment)
        else viewModel.agreedDocument(agreement)
    }

    AgreementStatusChangeDialog(dialogState = declineDialogState) { comment ->
        if (comment != null && comment.isNotBlank())
            viewModel.declineDocument(agreement, comment)
        else viewModel.declineDocument(agreement)
    }
}

@Composable
private fun AgreementStatusChangeDialog(
    dialogState: MaterialDialogState,
    onPositiveClick: (String?) -> Unit
) {
    var comment: String? by remember { mutableStateOf(null) }

    MaterialDialog(dialogState = dialogState, buttons = {
        positiveButton("Отправить", onClick = { onPositiveClick(comment) })
        negativeButton("Отмена")
    }) {
        message("Вы уверены, что хотите изменить статус согласования?")
        input(
            label = "Комментарий",
            hint = "Вы можете оставить комментарий...",
            onInput = { comment = it }
        )
    }
}