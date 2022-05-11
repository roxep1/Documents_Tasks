package com.bashkir.documentstasks.ui.components.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.airbnb.mvrx.Async
import com.bashkir.documentstasks.data.models.User
import com.vanpra.composematerialdialogs.MaterialDialogState

@Composable
fun AddUserDialog(
    dialogState: MaterialDialogState,
    addedPerformers: SnapshotStateList<User>,
    allUsers: Async<List<User>>,
    onUpdate: () -> Unit,
    banList: List<String> = listOf()
) = UsersDialog(
    dialogState = dialogState,
    addedUserIds = addedPerformers.map { it.id } + banList,
    allUsers = allUsers,
    onUpdate = onUpdate
) { ids ->
    addedPerformers.addAll(ids.map { id -> allUsers()!!.first { it.id == id } })
    dialogState.hide()
}