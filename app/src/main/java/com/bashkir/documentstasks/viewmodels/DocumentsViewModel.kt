package com.bashkir.documentstasks.viewmodels

import android.content.Context
import android.net.Uri
import androidx.navigation.NavController
import com.airbnb.mvrx.*
import com.bashkir.documentstasks.data.models.*
import com.bashkir.documentstasks.data.services.DocumentsService
import com.bashkir.documentstasks.ui.components.filters.DocumentFilterOption
import com.bashkir.documentstasks.ui.components.filters.DocumentFilterOption.*
import com.bashkir.documentstasks.utils.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

class DocumentsViewModel(
    initialState: DocumentsState,
    private val service: DocumentsService,
    private val context: Context
) : MavericksViewModel<DocumentsState>(initialState) {

    fun onCreate() {
        getAllDocuments()
        getAllUsers()
    }

    fun getAllDocuments() = suspend {
        service.getAllDocuments()
    }.execute(retainValue = DocumentsState::documents) { copy(documents = it) }

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

    fun downloadDocument(document: Document, uri: Uri?) = writeDocument(document, uri, context)

    fun addDocument(document: DocumentForm) = suspend {
        service.addDocument(document)
    }.execute {
        if (it is Success)
            getAllDocuments()
        copy(addingDocumentState = it)
    }

    fun agreedDocument(agreement: Agreement) = suspend {
        service.agreedDocument(agreement)
    }.execute {
        getAllDocuments()
        copy()
    }

    fun agreedDocument(agreement: Agreement, comment: String) = suspend {
        service.agreedDocument(agreement, comment)
    }.execute {
        getAllDocuments()
        copy()
    }

    fun declineDocument(agreement: Agreement) = suspend {
        service.declineDocument(agreement)
    }.execute {
        getAllDocuments()
        copy()
    }

    fun declineDocument(agreement: Agreement, comment: String) = suspend {
        service.declineDocument(agreement, comment)
    }.execute {
        getAllDocuments()
        copy()
    }

    fun updateDocument(document: Document, newFileUri: Uri?) =
        getBytesDocument(newFileUri, context)?.let { bytes ->
            getMetadata(newFileUri, context) { fileName, _ ->
                suspend {
                    service.updateDocument(document.run {
                        DocumentForm(
                            title,
                            bytes,
                            fileName?.getExtension() ?: extension,
                            desc,
                            listOf(),
                            listOf(),
                            author.toForm(),
                            templateId,
                            id
                        )
                    })
                }.execute {
                    if (it is Success)
                        getAllDocuments()

                    copy()
                }
            }
        }

    fun familiarizeDocument(familiarize: Familiarize) = suspend {
        service.familiarizeDocument(familiarize)
    }.execute {
        getAllDocuments()
        copy()
    }

    fun endAddingSession(navController: NavController) {
        setState { copy(addingDocumentState = Uninitialized) }
        navController.popBackStack()
    }

    fun getAllUsers() = suspend {
        service.getAllUsers()
    }.execute { copy(users = it) }

    fun getBytesDocument(uri: Uri?): ByteArray? = getBytesDocument(uri, context)
    fun getMetadata(uri: Uri?, onResult: (String?, Long?) -> Unit) =
        getMetadata(uri, context, onResult)

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
    val users: Async<List<User>> = Uninitialized,
    val addingDocumentState: Async<Unit> = Uninitialized,
) : MavericksState