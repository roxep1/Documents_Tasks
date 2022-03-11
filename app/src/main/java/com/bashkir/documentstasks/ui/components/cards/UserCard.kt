package com.bashkir.documentstasks.ui.components.cards

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bashkir.documentstasks.data.models.User
import com.bashkir.documentstasks.ui.components.Label
import com.bashkir.documentstasks.ui.theme.DocumentsTasksTheme.dimens
import com.bashkir.documentstasks.ui.theme.cardShape
import com.bashkir.documentstasks.ui.theme.normalText
import com.bashkir.documentstasks.ui.theme.titleText

@Composable
fun UserCard(
    modifier: Modifier = Modifier,
    user: User,
    deleteIconOnClick: (() -> Unit)? = null,
    onClick: (() -> Unit)? = null
) =
    Card(
        modifier = modifier
            .run {
                if (onClick != null)
                    clickable(onClick = onClick)
                else
                    this
            }
            .fillMaxWidth(),
        elevation = dimens.normalElevation,
        shape = cardShape
    ) {
        Row(
            Modifier.padding(dimens.normalPadding),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(user.fullName, style = titleText)
                Spacer(Modifier.height(dimens.articlePadding))
                Text(user.email, style = normalText)
            }
            deleteIconOnClick?.let { onClickIcon ->
                IconButton(onClick = onClickIcon) {
                    Icon(Icons.Default.Clear, "Delete button")
                }
            }
        }
    }

@Composable
fun PerformersList(
    performers: List<User>,
    deleteUserOnClick: ((User) -> Unit)? = null,
    addPerformerBtn: @Composable LazyItemScope.() -> Unit = {}
) = Column {
    Label("Исполнители: ")
    LazyColumn(
        Modifier
            .fillMaxWidth()
            .requiredHeight(dimens.maxListHeight)
            .padding(top = dimens.articlePadding)
    ) {
        items(performers) { user ->
            UserCard(
                Modifier.padding(top = dimens.articlePadding),
                user = user,
                deleteIconOnClick = if (deleteUserOnClick != null) {
                    { deleteUserOnClick(user) }
                } else null
            )
        }
        item(content = addPerformerBtn)
    }
}