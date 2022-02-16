package com.bashkir.documentstasks.data.models

import androidx.compose.ui.text.TextStyle
import com.bashkir.documentstasks.ui.theme.statusBlue
import com.bashkir.documentstasks.ui.theme.statusGray
import com.bashkir.documentstasks.ui.theme.statusGreen

enum class PerformStatus(val text: String, val textStyle: TextStyle){
    Waiting("Ожидает", statusGray),
    InProgress("В процессе", statusBlue),
    Completed("Сдано", statusGreen)
}
