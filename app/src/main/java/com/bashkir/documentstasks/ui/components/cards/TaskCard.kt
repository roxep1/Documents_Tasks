package com.bashkir.documentstasks.ui.components.cards

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.bashkir.documentstasks.R
import com.bashkir.documentstasks.data.models.Task
import com.bashkir.documentstasks.ui.components.loadingItem
import com.bashkir.documentstasks.ui.components.views.PerformersView
import com.bashkir.documentstasks.ui.theme.titleText
import com.bashkir.documentstasks.utils.formatCutToString
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun TaskCardList(
    modifier: Modifier = Modifier,
    tasks: List<Task> = listOf(),
    onDetailsClick: (Task) -> Unit,
    isLoading: Boolean,
    myId: String,
    onUpdate: () -> Unit
) = SwipeRefresh(state = rememberSwipeRefreshState(isLoading), onRefresh = onUpdate) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        items(tasks) { task ->
            TaskCard(task, task.author.id == myId) { onDetailsClick(task) }
        }
    }
}

@Composable
fun TaskCard(task: Task, isAuthor: Boolean, onDetailsClick: () -> Unit) =
    ExpandingCard(
        title = task.title,
        desc = task.desc,
        author = if (isAuthor) "Вы" else task.author.shortFullName,
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
            if (isAuthor)
                task.performs.PerformersView()
        },
        onDetailsClick = onDetailsClick
    )
