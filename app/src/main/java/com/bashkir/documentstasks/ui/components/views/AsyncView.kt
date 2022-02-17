package com.bashkir.documentstasks.ui.components

import androidx.compose.runtime.Composable
import com.airbnb.mvrx.Async
import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.Success

@Composable
fun <T> AsyncView(async: Async<T>, errorText: String, onSuccess: @Composable (T) -> Unit) =
    when (async) {
        is Success -> onSuccess(async())
        is Loading -> LoadingScreen()
        is Fail -> ErrorView(errorText, async)
        else -> ErrorView(errorText)
    }