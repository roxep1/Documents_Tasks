package com.bashkir.documentstasks.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class Dimensions(
    val normalPadding: Dp,
    val normalElevation : Dp,
    val titleText : TextUnit,
    val normalText : TextUnit,
    val articlePadding: Dp,
    val smallText: TextUnit,
    val smallPadding: Dp,
    val bottomNavHeight : Dp = 56.dp,
    val maxListHeight : Dp
)

val standardDimens = Dimensions(
    normalPadding = 10.dp,
    normalElevation = 8.dp,
    titleText = 16.sp,
    normalText = 14.sp,
    articlePadding = 5.dp,
    smallText = 12.sp,
    smallPadding = 3.dp,
    maxListHeight = 200.dp
)