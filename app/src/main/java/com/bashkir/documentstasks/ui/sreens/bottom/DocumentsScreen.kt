package com.bashkir.documentstasks.ui.sreens.bottom

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import com.airbnb.mvrx.compose.collectAsState
import com.bashkir.documentstasks.ui.components.anim.AnimateVertical
import com.bashkir.documentstasks.ui.components.buttons.AddFloatingButton
import com.bashkir.documentstasks.ui.components.cards.DocumentCardList
import com.bashkir.documentstasks.ui.components.cards.FilterSettingsCard
import com.bashkir.documentstasks.ui.components.filters.DocumentFilterOption
import com.bashkir.documentstasks.ui.components.topbars.ExtendedTopBarBottomNav
import com.bashkir.documentstasks.ui.components.views.AsyncView
import com.bashkir.documentstasks.ui.navigation.Screen
import com.bashkir.documentstasks.ui.theme.DocumentsTasksTheme
import com.bashkir.documentstasks.utils.navigate
import com.bashkir.documentstasks.viewmodels.DocumentsState
import com.bashkir.documentstasks.viewmodels.DocumentsViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DocumentsScreenBody(navController: NavController, viewModel: DocumentsViewModel) {

    val searchTextField = remember { mutableStateOf(TextFieldValue()) }
    val filterSettingsVisible = remember { mutableStateOf(false) }
    val documentFilterOption = remember { mutableStateOf(DocumentFilterOption.ALL) }
    val documents by viewModel.collectAsState(DocumentsState::documents)

    Scaffold(
        topBar = { ExtendedTopBarBottomNav(navController, searchTextField, filterSettingsVisible) },
        floatingActionButton = {
            AddFloatingButton(
                navController,
                "Новый документ",
                Screen.AddDocument.destination
            )
        }) {
        AsyncView(
            documents,
            "Не удалось загрузить документы",
            onUpdate = viewModel::getAllDocuments
        ) { loadedDocuments, isLoading ->
            DocumentCardList(
                modifier = Modifier.padding(it).padding(bottom = DocumentsTasksTheme.dimens.normalPadding),
                onDetailsClick = navController::navigate,
                documents = viewModel.filterDocuments(
                    loadedDocuments,
                    searchTextField.value.text,
                    documentFilterOption.value
                ),
                isLoading = isLoading,
                onUpdate = viewModel::getAllDocuments
            )
        }

        AnimateVertical(visible = filterSettingsVisible) {
            FilterSettingsCard(documentFilterOption) { filterSettingsVisible.value = false }
        }
    }
}