package com.bashkir.documentstasks.ui.components.cards

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.bashkir.documentstasks.R
import com.bashkir.documentstasks.data.models.Task
import com.bashkir.documentstasks.ui.components.filters.TaskFilterOption
import com.bashkir.documentstasks.ui.components.views.PerformersView
import com.bashkir.documentstasks.ui.theme.DocumentsTasksTheme.dimens
import com.bashkir.documentstasks.ui.theme.titleText
import com.bashkir.documentstasks.utils.formatCutToString

@Composable
fun TaskCardList(
    modifier: Modifier = Modifier,
    tasks: Map<Task, TaskFilterOption> = mapOf(),
    onDetailsClick: (Task) -> Unit,
    isLoading: Boolean,
    onUpdate: () -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        flingBehavior = object : FlingBehavior {
            override suspend fun ScrollScope.performFling(initialVelocity: Float): Float {
                onUpdate()
                return initialVelocity
            }
        }
    ) {
        item {
            if (isLoading)
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(dimens.normalPadding),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
        }
        items(tasks.toList()) { (task, isAuthor) ->
            TaskCard(task = task, isAuthor) { onDetailsClick(task) }
        }
    }
}

@Composable
fun TaskCard(task: Task, filterOption: TaskFilterOption, onDetailsClick: () -> Unit) =
    ExpandingCard(
        title = task.title,
        desc = task.desc,
        author = task.author,
        pubDate = task.pubDate,
        expandingButtonText = "${stringResource(R.string.performers)} ${task.performs.count()}",
        mainInfo = {
            MainInfo(Modifier.weight(0.5F)) {
                Text(
                    "Сдать до: ${task.deadline.formatCutToString()}",
                    style = titleText,
                    modifier = Modifier.align(CenterHorizontally)
                )
            }
        },
        expandedInfo = {
            if (filterOption == TaskFilterOption.ISSUED)
                task.performs.PerformersView()
        },
        onDetailsClick = onDetailsClick
    )
