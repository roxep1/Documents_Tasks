package com.bashkir.documentstasks.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.bashkir.documentstasks.ui.theme.DocumentsTasksTheme.dimens

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)

val grayText = TextStyle(
        color = Color.Gray,
        fontSize = 12.sp
    )

val titleText = TextStyle(
    fontSize = dimens.titleText,
    fontWeight = FontWeight.Bold
)

val statusGreen = TextStyle(
    fontSize = dimens.titleText,
    fontWeight = FontWeight.Bold,
    color = Color.Green
)

val statusGray = TextStyle(
    fontSize = dimens.titleText,
    fontWeight = FontWeight.Bold,
    color = Color.Gray
)

val statusBlue = TextStyle(
    fontSize = dimens.titleText,
    fontWeight = FontWeight.Bold,
    color = Color.Blue
)

val linkText = SpanStyle(
    color = Color.Blue,
    textDecoration = TextDecoration.Underline
)