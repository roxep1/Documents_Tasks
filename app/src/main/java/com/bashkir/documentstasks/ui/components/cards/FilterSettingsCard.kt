package com.bashkir.documentstasks.ui.components.cards

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Card
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.bashkir.documentstasks.ui.components.filters.FilterOption
import com.bashkir.documentstasks.ui.components.filters.TaskFilterOption
import com.bashkir.documentstasks.ui.theme.DocumentsTasksTheme
import com.bashkir.documentstasks.ui.theme.DocumentsTasksTheme.dimens
import com.bashkir.documentstasks.ui.theme.normalText
import com.bashkir.documentstasks.ui.theme.settingsCardShape


@Composable
inline fun <reified T> FilterSettingsCard(
    selectedOption: MutableState<T>,
    crossinline onSelect: () -> Unit = {}
) where T : Enum<T>, T : FilterOption {
    Card(
        Modifier
            .fillMaxWidth(),
        shape = settingsCardShape
    ) {
        Column(Modifier.padding(dimens.normalPadding)) {
            enumValues<T>().forEach { filterOption ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .selectable(selected = filterOption == selectedOption.value) {
                            selectedOption.value = filterOption
                            onSelect()
                        }
                ) {
                    RadioButton(
                        selected = filterOption == selectedOption.value,
                        onClick = {
                            selectedOption.value = filterOption
                            onSelect()
                        }
                    )
                    Text(
                        text = filterOption.getOption(),
                        style = normalText,
                        modifier = Modifier
                            .padding(start = dimens.articlePadding)
                            .align(CenterVertically),
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun TaskFilterSettingsCardPreview() = DocumentsTasksTheme {
    FilterSettingsCard(remember { mutableStateOf(TaskFilterOption.ALL) })
}