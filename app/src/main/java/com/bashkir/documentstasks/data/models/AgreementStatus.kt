package com.bashkir.documentstasks.data.models

import androidx.compose.ui.text.TextStyle
import com.bashkir.documentstasks.ui.theme.statusGray
import com.bashkir.documentstasks.ui.theme.statusGreen
import com.bashkir.documentstasks.ui.theme.statusRed

enum class AgreementStatus(val text: String, val textStyle: TextStyle) {
    Sent("Ожидает", statusGray),
    Agreed("Согласовано", statusGreen),
    Declined("Отказано", statusRed)
}