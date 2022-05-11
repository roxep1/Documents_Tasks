package com.bashkir.documentstasks.ui.components.views

import androidx.compose.runtime.Composable
import com.airbnb.mvrx.*
import com.bashkir.documentstasks.ui.components.LoadingScreen

@Composable
fun <T> AsyncView(
    async: Async<T>,
    errorText: String,
    onUpdate: () -> Unit,
    onSuccess: @Composable (T, Boolean) -> Unit
) =
    when (async) {
        is Success -> async().let { element ->
            if (element is List<*> && element.isEmpty())
                NoElementsView(onUpdate)
            else
                onSuccess(async(), false)
        }
        is Loading ->
            async().let { data ->
                if (data == null || data is List<*> && data.isEmpty())
                    LoadingScreen()
                else
                    onSuccess(data, true)
            }
        is Fail -> ErrorView(errorText, async, onUpdate)
        Uninitialized -> ErrorView("Не удалось отправить запрос")
    }