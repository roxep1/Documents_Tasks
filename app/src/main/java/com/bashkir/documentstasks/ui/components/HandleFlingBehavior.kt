package com.bashkir.documentstasks.ui.components

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollScope

class HandleFlingBehavior(private val onFling: () -> Unit) : FlingBehavior {
    override suspend fun ScrollScope.performFling(initialVelocity: Float): Float {
        onFling()
        return initialVelocity
    }
}