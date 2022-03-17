package com.bashkir.documentstasks.viewmodels

import com.airbnb.mvrx.*
import com.bashkir.documentstasks.data.models.Document
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

class DocumentsViewModel(initialState: DocumentsState) :
    MavericksViewModel<DocumentsState>(initialState) {

    companion object : MavericksViewModelFactory<DocumentsViewModel, DocumentsState>,
        KoinComponent {
        override fun create(
            viewModelContext: ViewModelContext,
            state: DocumentsState
        ): DocumentsViewModel = get { parametersOf(state) }
    }
}

data class DocumentsState(val documents: Async<List<Document>> = Uninitialized) : MavericksState