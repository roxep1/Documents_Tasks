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

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )

)

val normalText = TextStyle(
    fontSize = dimens.normalText
)

val smallText = TextStyle(
    fontSize = dimens.smallText
)

val placeHolderText = TextStyle(
    fontSize = dimens.normalText,
    fontStyle = FontStyle.Italic,
    color = Color.Gray
)

val graySmallText = TextStyle(
        color = Color.Gray,
        fontSize = dimens.smallText
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

val statusRed = TextStyle(
    fontSize = dimens.titleText,
    fontWeight = FontWeight.Bold,
    color = Color.Red
)

val linkText = SpanStyle(
    color = Color.Blue,
    textDecoration = TextDecoration.Underline
)