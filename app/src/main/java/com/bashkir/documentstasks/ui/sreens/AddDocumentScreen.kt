package com.bashkir.documentstasks.ui.sreens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.outlinedButtonColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.compose.collectAsState
import com.bashkir.documentstasks.contracts.DocumentSelectContract
import com.bashkir.documentstasks.data.models.AgreementForm
import com.bashkir.documentstasks.data.models.DocumentForm
import com.bashkir.documentstasks.data.models.User
import com.bashkir.documentstasks.ui.components.LoadingScreen
import com.bashkir.documentstasks.ui.components.cards.AgreementsList
import com.bashkir.documentstasks.ui.components.cards.FileCard
import com.bashkir.documentstasks.ui.components.cards.UsersList
import com.bashkir.documentstasks.ui.components.dialogs.AddAgreementDialog
import com.bashkir.documentstasks.ui.components.dialogs.AddUserDialog
import com.bashkir.documentstasks.ui.components.topbars.TopBar
import com.bashkir.documentstasks.ui.theme.DocumentsTasksTheme.dimens
import com.bashkir.documentstasks.ui.theme.Purple200
import com.bashkir.documentstasks.ui.theme.placeHolderText
import com.bashkir.documentstasks.ui.theme.titleText
import com.bashkir.documentstasks.utils.getExtension
import com.bashkir.documentstasks.utils.toFamiliarizeForms
import com.bashkir.documentstasks.viewmodels.DocumentsViewModel
import com.vanpra.composematerialdialogs.rememberMaterialDialogState

@Composable
fun AddDocumentScreenBody(navController: NavController, viewModel: DocumentsViewModel) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TopBar("Новый документ", navController) },
        snackbarHost = {
            SnackbarHost(it) { data ->
                Snackbar(
                    snackbarData = data
                )
            }
        }
    ) {
        var documentTitle by remember { mutableStateOf(TextFieldValue()) }
        var documentDesc by remember { mutableStateOf(TextFieldValue()) }
        val documentFamiliarizes = remember { mutableStateListOf<User>() }
        val documentAgreements = remember { mutableStateListOf<AgreementForm>() }
        var isFamiliarizes by remember { mutableStateOf(false) }

        var documentByteArray: ByteArray? by remember { mutableStateOf(null) }
        var fileDisplayName: String? by remember { mutableStateOf(null) }
        var fileSize: Long? by remember { mutableStateOf(null) }

        val usersDialogState = rememberMaterialDialogState()
        val users by viewModel.collectAsState { it.users }

        val addingDocumentState by viewModel.collectAsState { it.addingDocumentState }

        LaunchedEffect(addingDocumentState) {
            if (addingDocumentState is Fail)
                scaffoldState.snackbarHostState
                    .showSnackbar("Не удалось загрузить документ")

            if (addingDocumentState is Success)
                viewModel.endAddingSession(navController)
        }

        if (addingDocumentState !is Loading)
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(dimens.normalPadding)
            ) {
                OutlinedTextField(
                    modifier = Modifier.padding(bottom = dimens.normalPadding),
                    value = documentTitle,
                    onValueChange = { documentTitle = it },
                    placeholder = { Text("Название", style = placeHolderText) }
                )

                OutlinedTextField(
                    modifier = Modifier.padding(bottom = dimens.normalPadding),
                    value = documentDesc,
                    onValueChange = { documentDesc = it },
                    placeholder = { Text("Описание", style = placeHolderText) }
                )

                val openDocLauncher =
                    rememberLauncherForActivityResult(
                        contract = DocumentSelectContract(),
                        onResult = {
                            documentByteArray = viewModel.getBytesDocument(it)
                            viewModel.getMetadata(it) { displayName, size ->
                                fileDisplayName = displayName
                                fileSize = size
                            }
                        })

                documentByteArray?.let {
                    FileCard(fileDisplayName, fileSize)
                }

                OutlinedButton(
                    onClick = { openDocLauncher.launch(0) },
                    Modifier
                        .align(CenterHorizontally)
                        .fillMaxWidth()
                        .padding(
                            dimens.normalPadding
                        )
                ) {
                    Text("Загрузить документ")
                }

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    OutlinedButton(
                        onClick = { isFamiliarizes = true },
                        Modifier.padding(dimens.normalPadding),
                        colors = if (isFamiliarizes) outlinedButtonColors(backgroundColor = Purple200) else outlinedButtonColors()
                    ) {
                        Text("Ознакомление", color = if (isFamiliarizes) Color.Black else Purple200)
                    }

                    OutlinedButton(
                        onClick = { isFamiliarizes = false },
                        Modifier.padding(dimens.normalPadding),
                        colors = if (!isFamiliarizes) outlinedButtonColors(backgroundColor = Purple200) else outlinedButtonColors()
                    ) {
                        Text(
                            "Согласование",
                            color = if (!isFamiliarizes) Color.Black else Purple200
                        )
                    }
                }
                if (isFamiliarizes)
                    AddUserDialog(
                        usersDialogState,
                        documentFamiliarizes,
                        users,
                        banList = documentAgreements.map { it.user.id },
                        onUpdate = viewModel::getAllUsers
                    )
                else
                    AddAgreementDialog(
                        usersDialogState,
                        documentAgreements,
                        users,
                        banList = documentFamiliarizes.map { it.id },
                        onUpdate = viewModel::getAllUsers
                    )

                users()?.let { usersList ->
                    if (isFamiliarizes)
                        UsersList(
                            users = documentFamiliarizes,
                            deleteUserOnClick = documentFamiliarizes::remove,
                            label = "На ознакомление"
                        ) {
                            OutlinedButton(onClick = usersDialogState::show) {
                                Text("Добавить на ознакомление")
                            }
                        }
                    else
                        AgreementsList(
                            users = documentAgreements.map { agreement ->
                                usersList.first { it.id == agreement.user.id }
                            },
                            agreements = documentAgreements,
                            deleteUserOnClick = documentAgreements::remove,
                            label = "На согласование"
                        ) {
                            OutlinedButton(onClick = usersDialogState::show) {
                                Text("Добавить на согласование")
                            }
                        }
                }

                fun documentIsValid(): Boolean =
                    documentTitle.text.isNotBlank() && documentByteArray != null &&
                            (documentAgreements.isNotEmpty() || documentFamiliarizes.isNotEmpty())

                Button(
                    onClick = {
                        viewModel.addDocument(
                            DocumentForm(
                                documentTitle.text,
                                documentByteArray!!,
                                fileDisplayName!!.getExtension(),
                                documentDesc.text.ifBlank { null },
                                documentFamiliarizes.toFamiliarizeForms(),
                                documentAgreements,
                            )
                        )
                    },
                    Modifier
                        .fillMaxWidth()
                        .padding(dimens.normalPadding),
                    enabled = documentIsValid()
                ) {
                    Text("Добавить документ", style = titleText)
                }
            }
        else LoadingScreen()
    }
}