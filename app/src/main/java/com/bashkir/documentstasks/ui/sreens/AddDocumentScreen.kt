package com.bashkir.documentstasks.ui.sreens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.outlinedButtonColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import com.airbnb.mvrx.compose.collectAsState
import com.bashkir.documentstasks.data.models.User
import com.bashkir.documentstasks.ui.components.cards.UsersList
import com.bashkir.documentstasks.ui.components.dialogs.AddUserDialog
import com.bashkir.documentstasks.ui.components.topbars.TopBar
import com.bashkir.documentstasks.ui.theme.DocumentsTasksTheme.dimens
import com.bashkir.documentstasks.ui.theme.Purple200
import com.bashkir.documentstasks.ui.theme.placeHolderText
import com.bashkir.documentstasks.viewmodels.DocumentsViewModel
import com.vanpra.composematerialdialogs.rememberMaterialDialogState

@Composable
fun AddDocumentScreenBody(navController: NavController, viewModel: DocumentsViewModel) = Scaffold(
    topBar = { TopBar("Новый документ", navController) }
) {
    var documentTitle by remember { mutableStateOf(TextFieldValue()) }
    var documentDesc by remember { mutableStateOf(TextFieldValue()) }
    var documentByteArray: ByteArray? by remember { mutableStateOf(null) }
    val documentFamiliarizes = remember { mutableStateListOf<User>() }
    val documentAgreements = remember { mutableStateListOf<User>() }
    var isFamiliarizes by remember { mutableStateOf(false) }

    val usersDialogState = rememberMaterialDialogState()
    val users by viewModel.collectAsState { it.users }

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

        OutlinedButton(
            onClick = { documentByteArray = viewModel.uploadDocument() },
            Modifier
                .align(CenterHorizontally)
                .fillMaxWidth()
                .padding(
                    dimens.normalPadding
                )
        ) {
            Text("Загрузить документ")
        }

        Row {
            OutlinedButton(
                onClick = { isFamiliarizes = true },
                Modifier.padding(dimens.normalPadding),
                colors = if (isFamiliarizes) outlinedButtonColors(backgroundColor = Purple200) else outlinedButtonColors()
            ) {
                Text("Ознакомление", color = if(isFamiliarizes) Color.Black else Purple200)
            }

            OutlinedButton(
                onClick = { isFamiliarizes = false },
                Modifier.padding(dimens.normalPadding),
                colors = if (!isFamiliarizes) outlinedButtonColors(backgroundColor = Purple200) else outlinedButtonColors()
            ) {
                Text("Согласование", color = if(!isFamiliarizes) Color.Black else Purple200)
            }
        }

        AddUserDialog(
            usersDialogState,
            if (isFamiliarizes) documentFamiliarizes else documentAgreements,
            users
        )

        UsersList(
            users = if (isFamiliarizes) documentFamiliarizes else documentAgreements,
            deleteUserOnClick = { user ->
                if (isFamiliarizes) documentFamiliarizes.remove(user)
                else documentAgreements.remove(user)
            },
            label = if (isFamiliarizes) "На ознакомление" else "На согласование"
        ) {
            Row(Modifier.fillParentMaxWidth(), horizontalArrangement = Arrangement.Center) {
                OutlinedButton(onClick = {
                    usersDialogState.show()
                }) {
                    Text(if (isFamiliarizes) "Добавить на ознакомление" else "Добавить на согласование")
                }
            }
        }
    }
}