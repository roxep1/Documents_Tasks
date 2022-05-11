package com.bashkir.documentstasks.ui.components.cards

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.bashkir.documentstasks.R
import com.bashkir.documentstasks.data.models.User
import com.bashkir.documentstasks.ui.theme.DocumentsTasksTheme.dimens
import com.bashkir.documentstasks.ui.theme.cardShape
import com.bashkir.documentstasks.ui.theme.graySmallText
import com.bashkir.documentstasks.ui.theme.titleText
import com.bashkir.documentstasks.utils.formatCutToString
import java.time.LocalDateTime

@Composable
fun ExpandingCard(
    title: String? = null,
    desc: String? = null,
    author: String,
    pubDate: LocalDateTime,
    expandingButtonText: String = "",
    mainInfo: @Composable RowScope.(Boolean) -> Unit = {},
    expandedInfo: @Composable ColumnScope.() -> Unit = {},
    onDetailsClick: () -> Unit = {}
) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = dimens.normalPadding,
                top = dimens.normalPadding,
                end = dimens.normalPadding
            )
            .clickable { isExpanded = !isExpanded }
            .animateContentSize(),
        elevation = dimens.normalElevation,
        shape = cardShape
    ) {
        Column(modifier = Modifier.padding(dimens.normalPadding)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TitleAndDesc(title = title, desc = desc, isExpanded = isExpanded)
                mainInfo(isExpanded)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = dimens.normalPadding),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                DownInfo(pubDate = pubDate, author = author)
                ExpandingButton(text = expandingButtonText, isExpanded)
            }
            if (isExpanded) {
                expandedInfo()
                CardButton(
                    Modifier.align(Alignment.CenterHorizontally),
                    stringResource(R.string.task_details_button),
                    onDetailsClick
                )
            }
        }
    }
}

@Composable
private fun CardButton(modifier: Modifier = Modifier, text: String, onClick: () -> Unit) =
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .padding(horizontal = dimens.normalPadding)
            .padding(top = dimens.normalPadding)
    ) {
        Text(text, style = titleText)
    }

@Composable
private fun RowScope.TitleAndDesc(title: String?, desc: String?, isExpanded: Boolean) = Column(
    modifier = Modifier
        .weight(1f)
        .padding(end = dimens.normalPadding),
    verticalArrangement = Arrangement.SpaceBetween
) {
    title?.let {
        if (it.isNotBlank())
            Text(
                title,
                style = titleText
            )
    }
    desc?.let {
        if (it.isNotBlank()) {
            Spacer(modifier = Modifier.height(dimens.articlePadding))
            Text(
                desc,
                maxLines = if (isExpanded) Int.MAX_VALUE else 2,
                fontSize = dimens.normalText
            )
        }
    }
}

@Composable
private fun DownInfo(pubDate: LocalDateTime, author: String) = Row(modifier = Modifier) {
    Text(pubDate.formatCutToString(), style = graySmallText)
    Spacer(modifier = Modifier.width(dimens.normalPadding))
    Text("Автор: $author", style = graySmallText)
}

@Composable
private fun ExpandingButton(text: String, isExpanded: Boolean) =
    Row(verticalAlignment = Alignment.Bottom) {
        Text(
            text,
            style = graySmallText
        )
        Icon(
            painterResource(if (isExpanded) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down),
            "arrow down"
        )
    }

@Composable
fun MainInfo(modifier: Modifier = Modifier, content: @Composable (ColumnScope.() -> Unit)) =
    Column(
        modifier = modifier.padding(start = dimens.normalPadding),
        verticalArrangement = Arrangement.Center,
        content = content
    )