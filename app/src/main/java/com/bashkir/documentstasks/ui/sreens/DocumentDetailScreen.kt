package com.bashkir.documentstasks.ui.sreens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.compose.collectAsState
import com.bashkir.documentstasks.contracts.DocumentCreateContract
import com.bashkir.documentstasks.contracts.DocumentSelectContract
import com.bashkir.documentstasks.data.models.*
import com.bashkir.documentstasks.ui.components.Label
import com.bashkir.documentstasks.ui.components.buttons.StyledTextButton
import com.bashkir.documentstasks.ui.components.topbars.TopBar
import com.bashkir.documentstasks.ui.components.views.AgreementsView
import com.bashkir.documentstasks.ui.components.views.AsyncLoadingView
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
    val loadingState by viewModel.collectAsState { it.loadingState }
    var title by remember { mutableStateOf("Документ") }

    AsyncLoadingView(loadingState) {
        Scaffold(
            topBar = { TopBar(title, navController) }
        ) {
            AsyncView(
                async = documents,
                errorText = "Неудалось загрузить документы",
                onUpdate = viewModel::getAllDocuments
            ) { loadedDocuments, _ ->
                loadedDocuments.find { document -> document.toDocument().id == documentId }
                    ?.let { document ->
                        title = document.toDocument().title
                        DocumentDetailView(document, viewModel, navController, it)
                    }
            }
        }
    }
}

@Composable
private fun DocumentDetailView(
    document: Documentable,
    viewModel: DocumentsViewModel,
    navController: NavController,
    paddingValues: PaddingValues
) =
    Column(
        Modifier
            .padding(dimens.normalPadding)
            .padding(paddingValues)
            .fillMaxSize()
    ) {

        document.toDocument().run {
            desc?.let {
                if (desc.isNotBlank()) {
                    Text(desc)
                    Spacer(Modifier.height(dimens.normalPadding))
                }
            }
            templateId?.let {
                Row {
                    Text("Шаблон документа: ")
                    Text(templateId, fontWeight = FontWeight.Bold)
                }
            }
        }

        val file by viewModel.collectAsState { it.file }
        val errorFile = rememberMaterialDialogState()
        val downloadDocLauncher =
            rememberLauncherForActivityResult(
                contract = DocumentCreateContract(),
                onResult = { viewModel.downloadDocument(file()!!, it) }
            )
        if (file !is Loading)
            StyledTextButton(
                Modifier.align(CenterHorizontally),
                "Скачать документ",
                onClick = {
                    viewModel.getFile(document.toDocument())
                }
            )
        else CircularProgressIndicator(Modifier.align(CenterHorizontally))
        MaterialDialog(errorFile) {
            title("Не удалось загрузить файл")
            message((file as Fail).error.message)
        }

        LaunchedEffect(file) {
            if (file is Success)
                downloadDocLauncher.launch(file())
            else if (file is Fail)
                errorFile.show()
        }

        when (document) {
            is Agreement -> AgreementView(document, viewModel)
            is Document -> if (viewModel.isAuthor(document)) DocumentView(
                document,
                viewModel,
                navController
            )
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
private fun ColumnScope.DocumentView(
    document: Document,
    viewModel: DocumentsViewModel,
    navController: NavController
) {
    val updateDocLauncher =
        rememberLauncherForActivityResult(
            contract = DocumentSelectContract(),
            onResult = { viewModel.updateDocument(document, it) }
        )
    val updateDocumentState by viewModel.collectAsState { it.updateDocumentState }
    val errorDialog = rememberMaterialDialogState()
    val deleteDialog = rememberMaterialDialogState()

    MaterialDialog(errorDialog) {
        title("Что-то пошло не так")
        message((updateDocumentState as Fail).error.message)
    }

    LaunchedEffect(updateDocumentState) {
        if (updateDocumentState is Fail)
            errorDialog.show()
    }

    if (updateDocumentState !is Loading)
        OutlinedButton(
            onClick = { updateDocLauncher.launch(0) },
            Modifier.align(CenterHorizontally)
        ) {
            Text("Обновить документ")
        }
    else CircularProgressIndicator(Modifier.align(CenterHorizontally))

    OutlinedButton(
        onClick = deleteDialog::show,
        Modifier.align(CenterHorizontally)
    ) {
        Text("Удалить документ")
    }

    MaterialDialog(deleteDialog, buttons = {
        positiveButton("Удалить") {
            viewModel.deleteDocument(document)
            navController.popBackStack()
        }
        negativeButton("Отмена")
    }) {
        title("Внимание")
        message("Вы точно хотите удалить этот документ?")
    }

    if (document.agreement.isNotEmpty()) {
        Label("На согласование отправлено:")
        document.agreement.AgreementsView()
        Spacer(Modifier.height(dimens.normalPadding))
    }
    if (document.familiarize.isNotEmpty()) {
        Label("На ознакомление отправлено:")
        LazyColumn(Modifier.align(CenterHorizontally)) {
            items(document.familiarize) { familiarize ->
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(familiarize.user.fullName)
                    Text(if (familiarize.checked) "Ознакомлен" else "Не ознакомлен")
                }
            }
        }
    }
}

@Composable
private fun AgreementView(agreement: Agreement, viewModel: DocumentsViewModel) {
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

    Column(Modifier.fillMaxWidth(), horizontalAlignment = CenterHorizontally) {
        if (agreement.status != AgreementStatus.Agreed)
            OutlinedButton(
                onClick = agreedDialogState::show
            ) {
                Text("Согласовать")
            }

        if (agreement.status != AgreementStatus.Declined)
            OutlinedButton(
                onClick = declineDialogState::show
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
            placeholder = "Вы можете оставить комментарий...",
            onInput = { comment = it }
        )
    }
}