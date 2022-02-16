package com.bashkir.documentstasks.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.airbnb.mvrx.Async
import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.Success
import com.bashkir.documentstasks.data.test.testUserList1
import com.bashkir.documentstasks.ui.components.cards.UserCard
import com.bashkir.documentstasks.ui.theme.DocumentsTasksTheme

@Composable
fun <T> AsyncView(async: Async<T>, errorText: String, onSuccess: @Composable (T) -> Unit) =
    when (async) {
        is Success -> onSuccess(async())
        is Loading -> LoadingScreen()
        is Fail -> ErrorView(errorText, async)
        else -> ErrorView(errorText)
    }