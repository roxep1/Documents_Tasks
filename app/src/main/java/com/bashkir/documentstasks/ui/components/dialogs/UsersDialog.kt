package com.bashkir.documentstasks.ui.components.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import com.airbnb.mvrx.Async
import com.bashkir.documentstasks.data.models.User
import com.bashkir.documentstasks.ui.components.SearchTextField
import com.bashkir.documentstasks.ui.components.cards.UserCard
import com.bashkir.documentstasks.ui.components.views.AsyncView
import com.bashkir.documentstasks.ui.theme.DocumentsTasksTheme
import com.bashkir.documentstasks.utils.filter
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogScope
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.customView

@Composable
fun UsersDialog(
    dialogState: MaterialDialogState,
    addedUserIds: List<String>,
    allUsers: Async<List<User>>,
    isValid: Boolean = true,
    onBackClick: () -> Unit = {},
    onUpdate: () -> Unit,
    onAddClick: (List<String>) -> Unit
) {
    val selectedUserIds = remember { mutableStateListOf<String>() }
    MaterialDialog(dialogState = dialogState, buttons = {
        positiveButton("Добавить") { onAddClick(selectedUserIds) }
        negativeButton("Закрыть", onClick = onBackClick)
    }) {
        customView {
            AsyncView(allUsers, "Не удалось загрузить пользователей", onUpdate = onUpdate) { users, _ ->
                val searchTextField = remember { mutableStateOf(TextFieldValue()) }
                Column {
                    SearchTextField(searchTextState = searchTextField)
                    LazyColumn {
                        items(users
                            .filter { !addedUserIds.contains(it.id) }
                            .filter(searchTextField.value.text)) { user ->
                            UserCard(
                                Modifier
                                    .padding(top = DocumentsTasksTheme.dimens.articlePadding)
                                    .padding(horizontal = DocumentsTasksTheme.dimens.normalPadding),
                                user = user,
                                onClick = {
                                    if (selectedUserIds.contains(it.id))
                                        selectedUserIds.remove(it.id)
                                    else selectedUserIds.add(it.id)
                                },
                                selected = selectedUserIds.contains(user.id)
                            )
                        }
                    }
                }
            }
        }
        PositiveButtonEnabled(valid = selectedUserIds.isNotEmpty() && isValid) {}
    }
}