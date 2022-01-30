package com.bashkir.documentstasks.ui.components.anim

import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@ExperimentalAnimationApi
@Composable
fun AnimateVertical(
    visible: MutableState<Boolean>,
    content: @Composable AnimatedVisibilityScope.() -> Unit
) = AnimatedVisibility(
    visible = visible.value,
    enter = slideInVertically(initialOffsetY = { -it }),
    exit = slideOutVertically(targetOffsetY = { -it }),
    content = content
)