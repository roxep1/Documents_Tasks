package com.bashkir.documentstasks.viewmodels

import com.airbnb.mvrx.*
import com.bashkir.documentstasks.data.models.*
import com.bashkir.documentstasks.data.services.DocumentsService
import com.bashkir.documentstasks.ui.components.filters.DocumentFilterOption
import com.bashkir.documentstasks.ui.components.filters.DocumentFilterOption.*
import com.bashkir.documentstasks.utils.filter
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

class DocumentsViewModel(initialState: DocumentsState, private val service: DocumentsService) :
    MavericksViewModel<DocumentsState>(initialState) {

    fun onCreate() {
        getAllDocuments()
        getAllUsers()
    }

    private fun getAllDocuments() = suspend {
        service.getAllDocuments()
    }.execute { copy(documents = it) }

    fun filterDocuments(
        documents: List<Documentable>,
        searchText: String,
        filterOption: DocumentFilterOption
    ): List<Documentable> =
        when (filterOption) {
            ALL -> documents
            AGREEMENT -> documents.filterIsInstance<Agreement>()
            FAMILIARIZE -> documents.filterIsInstance<Familiarize>()
            ISSUED -> documents.filterIsInstance<Document>()
        }.filter(searchText)

    fun downloadDocument(document: Document) {
        TODO()
    }

    fun agreedDocument(agreement: Agreement) {
        TODO()
    }

    fun agreedDocument(agreement: Agreement, comment: String) {
        TODO()
    }

    fun declineDocument(agreement: Agreement) {
        TODO("Not yet implemented")
    }

    fun declineDocument(agreement: Agreement, comment: String) {
        TODO()
    }

    fun updateDocument() {
        TODO()
    }

    fun familiarizeDocument(familiarize: Familiarize) {
        TODO("Not yet implemented")
    }

    fun uploadDocument(): ByteArray {
        TODO()
    }

    private fun getAllUsers() = suspend {
        service.getAllUsers()
    }.execute { copy(users = it) }

    companion object : MavericksViewModelFactory<DocumentsViewModel, DocumentsState>,
        KoinComponent {
        override fun create(
            viewModelContext: ViewModelContext,
            state: DocumentsState
        ): DocumentsViewModel = get { parametersOf(state) }
    }
}

data class DocumentsState(
    val documents: Async<List<Documentable>> = Uninitialized,
    val users: Async<List<User>> = Uninitialized
) : MavericksState