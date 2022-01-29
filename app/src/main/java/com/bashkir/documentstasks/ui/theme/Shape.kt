package com.bashkir.documentstasks.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(0.dp)
)

val cardShape = RoundedCornerShape(8.dp)

val settingsCardShape = RoundedCornerShape(bottomStartPercent = 25, bottomEndPercent = 25)