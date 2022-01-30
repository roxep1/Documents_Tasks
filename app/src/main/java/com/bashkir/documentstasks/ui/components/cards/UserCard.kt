package com.bashkir.documentstasks.ui.components.cards

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bashkir.documentstasks.data.models.User
import com.bashkir.documentstasks.ui.theme.DocumentsTasksTheme.dimens
import com.bashkir.documentstasks.ui.theme.cardShape
import com.bashkir.documentstasks.ui.theme.normalText
import com.bashkir.documentstasks.ui.theme.titleText

@Composable
fun UserCard(modifier: Modifier = Modifier, user: User, onClick: (() -> Unit)? = null) =
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
        Column(Modifier.padding(dimens.normalPadding)) {
            Text(user.name.fullName, style = titleText)
            Spacer(Modifier.height(dimens.articlePadding))
            Text(user.email, style = normalText)
        }
    }