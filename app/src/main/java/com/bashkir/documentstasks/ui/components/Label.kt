package com.bashkir.documentstasks.ui.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.bashkir.documentstasks.ui.theme.graySmallText

@Composable
fun Label(label: String) = Text(label, style = graySmallText)