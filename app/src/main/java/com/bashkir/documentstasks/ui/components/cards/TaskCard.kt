package com.bashkir.documentstasks.ui.components.cards

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.bashkir.documentstasks.R
import com.bashkir.documentstasks.data.models.Task
import com.bashkir.documentstasks.ui.components.views.PerformersView
import com.bashkir.documentstasks.ui.theme.DocumentsTasksTheme.dimens
import com.bashkir.documentstasks.ui.theme.cardShape
import com.bashkir.documentstasks.ui.theme.graySmallText
import com.bashkir.documentstasks.ui.theme.titleText
import com.bashkir.documentstasks.utils.formatCutToString

@Composable
fun TaskCardList(
    modifier: Modifier = Modifier,
    tasks: Map<Task, Boolean> = mapOf(),
    onDetailsClick: (Task) -> Unit
) = LazyColumn(modifier = modifier.fillMaxSize()) {
    items(tasks.toList()) { (task, isAuthor) ->
        TaskCard(task = task, isAuthor) { onDetailsClick(task) }
    }
}

@Composable
fun TaskCard(
    task: Task,
    isAuthor: Boolean,
    onDetailsClick: () -> Unit
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
                TitleAndDesc(task)
                DeadLine(task)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = dimens.normalPadding),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                DownInfo(task)
                PerformersButton(task, isExpanded)
            }
            if (isExpanded) {
                if (isAuthor)
                    task.performs.PerformersView()
                CardButton(
                    Modifier.align(CenterHorizontally),
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
private fun PerformersButton(task: Task, isExpanded: Boolean) =
    Row(verticalAlignment = Alignment.Bottom) {
        Text(
            "${stringResource(R.string.performers)} ${task.performs.count()}",
            style = graySmallText
        )
        Icon(
            painterResource(if (isExpanded) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down),
            "arrow down"
        )
    }

@Composable
private fun DownInfo(task: Task) = Row(modifier = Modifier) {
    Text(task.pubDate.formatCutToString(), style = graySmallText)
    Spacer(modifier = Modifier.width(dimens.normalPadding))
    Text("Автор: ${task.author.fullName}", style = graySmallText)
}

@Composable
private fun RowScope.DeadLine(task: Task) = Column(
    modifier = Modifier
        .weight(0.5f)
        .padding(start = dimens.normalPadding),
    verticalArrangement = Arrangement.SpaceBetween
) {
    Text(
        "Сдать до: ${task.deadline.formatCutToString()}",
        style = titleText,
        modifier = Modifier.align(CenterHorizontally)
    )
}

@Composable
private fun RowScope.TitleAndDesc(task: Task) = Column(
    modifier = Modifier
        .weight(1f)
        .padding(end = dimens.normalPadding),
    verticalArrangement = Arrangement.SpaceBetween
) {
    Text(
        task.title,
        style = titleText
    )
    Spacer(modifier = Modifier.height(dimens.articlePadding))
    Text(task.desc, maxLines = 2, fontSize = dimens.normalText)
}