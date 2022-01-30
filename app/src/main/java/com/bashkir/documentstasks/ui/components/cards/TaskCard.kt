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
import androidx.compose.ui.tooling.preview.Preview
import com.bashkir.documentstasks.R
import com.bashkir.documentstasks.data.models.Task
import com.bashkir.documentstasks.data.test.testTask1
import com.bashkir.documentstasks.data.test.testTasksList1
import com.bashkir.documentstasks.ui.components.ShowPerformers
import com.bashkir.documentstasks.ui.theme.DocumentsTasksTheme
import com.bashkir.documentstasks.ui.theme.DocumentsTasksTheme.dimens
import com.bashkir.documentstasks.ui.theme.cardShape
import com.bashkir.documentstasks.ui.theme.graySmallText
import com.bashkir.documentstasks.ui.theme.titleText
import com.bashkir.documentstasks.utils.formatCutToString

@Composable
fun TaskCardList(
    tasks: List<Task> = listOf(),
    onClick: (Task) -> Unit
) = LazyColumn(modifier = Modifier.fillMaxSize()) {
    items(tasks) { task ->
        TaskCard(task = task) { onClick(task) }
    }
}

@Composable
fun TaskCard(task: Task, onClick: () -> Unit) {
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
                Column(
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
                Column(
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
//                    Spacer(modifier = Modifier.height(dimens.articlePadding))
//                    Row(modifier = Modifier.align(CenterHorizontally)) {
//                        Text(
//                            "Статус: ",
//                            style = titleText
//                        )
//                        Text(
//                            task.performers.
//                        )
//                    }
                }

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = dimens.normalPadding),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Row(modifier = Modifier) {
                    Text(task.pubDate.formatCutToString(), style = graySmallText)
                    Spacer(modifier = Modifier.width(dimens.normalPadding))
                    Text("Автор: ${task.author.name.fullName}", style = graySmallText)
                }
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        "${stringResource(R.string.performers)} ${task.performers.count()}",
                        style = graySmallText
                    )
                    Icon(
                        painterResource(if (isExpanded) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down),
                        "arrow down"
                    )
                }
            }
            if (isExpanded) {
                task.performers.ShowPerformers()
                OutlinedButton(
                    onClick = onClick,
                    modifier = Modifier
                        .padding(
                            top = dimens.normalPadding,
                            end = dimens.normalPadding,
                            start = dimens.normalPadding
                        )
                        .align(CenterHorizontally)
                        .fillMaxWidth()
                ) {
                    Text(stringResource(R.string.task_details_button), style = titleText)
                }
            }
        }
    }
}

@Preview
@Composable
private fun TaskCardPreview() = TaskCard(
    task = testTask1
) {}

@Preview
@Composable
private fun TaskCardListPreview() = DocumentsTasksTheme {
    TaskCardList(testTasksList1, onClick = {})
}
