package com.bashkir.documentstasks.ui.components.dialogs

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import com.airbnb.mvrx.Async
import com.bashkir.documentstasks.data.models.User
import com.bashkir.documentstasks.ui.components.AsyncView
import com.bashkir.documentstasks.ui.components.cards.UserCard
import com.bashkir.documentstasks.ui.theme.DocumentsTasksTheme
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.customView

@Composable
fun PerformerAddDialog(
    dialogState: MaterialDialogState,
    addedPerformers: SnapshotStateList<User>,
    allUsers: Async<List<User>>
) =
    MaterialDialog(dialogState = dialogState, buttons = {
        negativeButton("Отмена")
    }) {
        customView {
            AsyncView(allUsers, "Не удалось загрузить пользователей") {
                LazyColumn {
                    items(it.filter { !addedPerformers.contains(it) }) { user ->
                        UserCard(
                            Modifier
                                .padding(top = DocumentsTasksTheme.dimens.articlePadding)
                                .padding(horizontal = DocumentsTasksTheme.dimens.normalPadding),
                            user = user
                        ) {
                            addedPerformers.add(user)
                            dialogState.hide()
                        }
                    }
                }
            }
        }
    }