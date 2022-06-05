package com.bashkir.documentstasks.ui.components.views

import androidx.compose.runtime.Composable
import com.airbnb.mvrx.Async
import com.airbnb.mvrx.Loading
import com.bashkir.documentstasks.ui.components.LoadingScreen

@Composable
fun AsyncLoadingView(async: Async<*>, content: @Composable () -> Unit) =
    if (async is Loading) LoadingScreen()
    else content()