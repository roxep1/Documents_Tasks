package com.bashkir.documentstasks.ui.components.dialogs

import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.airbnb.mvrx.Async
import com.bashkir.documentstasks.data.models.AgreementForm
import com.bashkir.documentstasks.data.models.User
import com.bashkir.documentstasks.utils.plus
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.LocalDateTime

@Composable
fun AddAgreementDialog(
    dialogState: MaterialDialogState,
    addedAgreements: SnapshotStateList<AgreementForm>,
    allUsers: Async<List<User>>
) {
    var deadline by remember { mutableStateOf(LocalDateTime.now()) }
    val usersState = rememberMaterialDialogState()
    val timePickerState = rememberMaterialDialogState()

    UsersDialog(
        dialogState = usersState,
        addedUserIds = addedAgreements.map { it.user.id },
        allUsers = allUsers,
        onBackClick = timePickerState::show
    ) { userIds ->
        userIds.forEach { id ->
            addedAgreements.add(
                AgreementForm(
                    allUsers()!!.first { it.id == id }.toForm(),
                    deadline
                )
            )
        }
    }

    DatePickerDialog(
        dialogState = dialogState,
        "Выберете дату срока сдачи",
        validator = { it.isAfter(LocalDate.now()) }) {
        deadline = it.atStartOfDay()
        timePickerState.show()
    }

    TimePickerDialog(
        dialogState = timePickerState,
        "Выберете время срока сдачи",
        onBackClick = dialogState::show
    ) {
        deadline = deadline.plus(it)
        usersState.show()
    }
}