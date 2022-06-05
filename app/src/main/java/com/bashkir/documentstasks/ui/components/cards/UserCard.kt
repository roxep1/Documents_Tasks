package com.bashkir.documentstasks.ui.components.cards

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.bashkir.documentstasks.data.models.AgreementForm
import com.bashkir.documentstasks.data.models.User
import com.bashkir.documentstasks.ui.components.Label
import com.bashkir.documentstasks.ui.components.buttons.DeleteButton
import com.bashkir.documentstasks.ui.theme.DocumentsTasksTheme.dimens
import com.bashkir.documentstasks.ui.theme.Purple200
import com.bashkir.documentstasks.ui.theme.cardShape
import com.bashkir.documentstasks.ui.theme.normalText
import com.bashkir.documentstasks.ui.theme.titleText
import com.bashkir.documentstasks.utils.formatToString

@Composable
fun UserCard(
    modifier: Modifier = Modifier,
    user: User,
    selected: Boolean = false,
    deleteIconOnClick: (() -> Unit)? = null,
    additionalInfo: @Composable (Color) -> Unit = {},
    onClick: ((User) -> Unit)? = null
) =
    Card(
        modifier = modifier
            .run {
                if (onClick != null)
                    clickable(onClick = { onClick(user) })
                else this
            }
            .fillMaxWidth(),
        elevation = dimens.normalElevation,
        shape = cardShape,
        backgroundColor = if (selected) Purple200 else MaterialTheme.colors.surface
    ) {
        Row(
            Modifier.padding(dimens.normalPadding),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    user.fullName,
                    style = titleText,
                    color = if (selected) MaterialTheme.colors.surface else Purple200
                )
                Spacer(Modifier.height(dimens.articlePadding))
                Text(
                    user.email,
                    style = normalText,
                    color = if (selected) MaterialTheme.colors.surface else Purple200
                )
                additionalInfo(if (selected) MaterialTheme.colors.surface else Purple200)
            }
            deleteIconOnClick?.let { onClickIcon ->
                DeleteButton(onClick = onClickIcon)
            }
        }
    }

@Composable
private fun List(
    label: String,
    addUserBtn: @Composable () -> Unit,
    content: @Composable () -> Unit
) = Column {
    Label("$label: ")
    Column(
        Modifier
            .fillMaxWidth()
//            .height(dimens.maxListHeight)
            .padding(top = dimens.articlePadding)
    ) {
        content()
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            addUserBtn()
        }
    }
}

@Composable
fun UsersList(
    users: List<User>,
    deleteUserOnClick: ((User) -> Unit)? = null,
    label: String = "Пользователи",
    addUserBtn: @Composable () -> Unit = {}
) = List(label, addUserBtn) {
    users.forEach { user ->
        UserCard(
            Modifier.padding(top = dimens.articlePadding),
            user = user,
            deleteIconOnClick = if (deleteUserOnClick != null) {
                { deleteUserOnClick(user) }
            } else null
        )
    }
}

@Composable
fun AgreementsList(
    users: List<User>,
    agreements: List<AgreementForm>,
    deleteUserOnClick: ((AgreementForm) -> Unit)? = null,
    label: String = "Согласования",
    addUserBtn: @Composable () -> Unit = {}
) = List(label, addUserBtn) {
    users.forEach { user ->
        agreements.first { it.user.id == user.id }.let { agreement ->
            UserCard(
                Modifier.padding(top = dimens.articlePadding),
                user = user,
                deleteIconOnClick = if (deleteUserOnClick != null) {
                    { deleteUserOnClick(agreement) }
                } else null,
                additionalInfo = {
                    Spacer(Modifier.height(dimens.articlePadding))
                    Text(
                        "Срок согласования: ${agreement.deadline.formatToString()}",
                        style = normalText,
                        color = it
                    )
                }
            )
        }
    }
}