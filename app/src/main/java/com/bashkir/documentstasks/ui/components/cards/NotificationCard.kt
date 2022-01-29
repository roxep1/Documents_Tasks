package com.bashkir.documentstasks.ui.components

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.bashkir.documentstasks.data.models.Notifiable
import com.bashkir.documentstasks.data.test.testTask2
import com.bashkir.documentstasks.ui.theme.*
import com.bashkir.documentstasks.ui.theme.DocumentsTasksTheme.dimens
import com.bashkir.documentstasks.utils.formatToString

@Composable
fun NotificationCardList(
    notifications: List<Notifiable> = listOf(), navController: NavController
) = LazyColumn(modifier = Modifier.fillMaxSize()) {
    items(notifications) { notification ->
        NotificationCard(notification) {
            navController.navigate(notification.destination)
        }
    }
}

@Composable
fun NotificationCard(notification: Notifiable, onClick: (Int) -> Unit) = Card(
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
        Text(
            "Пользователь ${notification.author.name.fullName} добавил ${notification.subject} для вас.",
            style = titleText
        )
        Spacer(Modifier.height(dimens.articlePadding))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(notification.time.formatToString(), style = graySmallText)
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

@Preview
@Composable
fun NotificationCardPreview() = DocumentsTasksTheme {
    NotificationCard(notification = testTask2) {}
}
