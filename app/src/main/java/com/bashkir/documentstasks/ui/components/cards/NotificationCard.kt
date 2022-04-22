package com.bashkir.documentstasks.ui.components.cards

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.navigation.NavController
import com.bashkir.documentstasks.data.models.Notification
import com.bashkir.documentstasks.ui.components.buttons.DeleteButton
import com.bashkir.documentstasks.ui.theme.DocumentsTasksTheme.dimens
import com.bashkir.documentstasks.ui.theme.cardShape
import com.bashkir.documentstasks.ui.theme.graySmallText
import com.bashkir.documentstasks.ui.theme.linkText
import com.bashkir.documentstasks.ui.theme.titleText
import com.bashkir.documentstasks.utils.formatCutToString

@Composable
fun NotificationCardList(
    notifications: List<Notification> = listOf(),
    navController: NavController,
    onDelete: (Notification) -> Unit
) = LazyColumn(modifier = Modifier.fillMaxSize()) {
    items(notifications) { notification ->
        NotificationCard(notification, onDelete = { onDelete(notification) }) {
            navController.navigate(notification.destination)
        }
    }
}

@Composable
fun NotificationCard(notification: Notification, onDelete: () -> Unit, onClick: (Int) -> Unit) =
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = dimens.normalPadding,
                top = dimens.normalPadding,
                end = dimens.normalPadding
            ),
        elevation = dimens.normalElevation,
        shape = cardShape,
        backgroundColor = if (notification.checked) Color.Gray else MaterialTheme.colors.surface
    ) {
        Column(
            Modifier.padding(dimens.normalPadding)
        ) {
            Row {
                Text(
                    "Пользователь ${notification.author.toUser().fullName} ${notification.subject}.",
                    Modifier.weight(1F),
                    style = titleText
                )
                DeleteButton(Modifier.weight(0.1F), onClick = onDelete)
            }
            Spacer(Modifier.height(dimens.articlePadding))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(notification.time.formatCutToString(), style = graySmallText)
                ClickableText(text = annotatedLinkString, onClick = onClick)
            }
        }
    }

private val annotatedLinkString = buildAnnotatedString {
    val str = "Перейти"
    val endIndex = str.count()
    append(str)
    addStyle(
        style = linkText, start = 0, end = endIndex
    )
}
