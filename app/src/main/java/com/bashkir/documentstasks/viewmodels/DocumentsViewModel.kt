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
        }.filter(searchText).sortedBy { it.toDocument().created }

    fun downloadDocument(file: File, uri: Uri?) {
        writeDocument(file, uri, context)
        setState { copy(file = Uninitialized) }
    }

    fun addDocument(document: DocumentForm, file: FileForm) = suspend {
        service.addDocument(document, file)
    }.execute {
        if (it is Success)
            getAllDocuments()
        copy(addingDocumentState = it)
    }

    fun deleteDocument(document: Document) = suspend {
        service.deleteDocument(document)
    }.executeWithUpdate()

    fun agreedDocument(agreement: Agreement) = suspend {
        service.agreedDocument(agreement)
    }.executeWithUpdate()

    fun agreedDocument(agreement: Agreement, comment: String) = suspend {
        service.agreedDocument(agreement, comment)
    }.executeWithUpdate()

    fun declineDocument(agreement: Agreement) = suspend {
        service.declineDocument(agreement)
    }.executeWithUpdate()

    fun declineDocument(agreement: Agreement, comment: String) = suspend {
        service.declineDocument(agreement, comment)
    }.executeWithUpdate()

    fun updateDocument(document: Document, newFileUri: Uri?) =
        getBytesDocument(newFileUri, context)?.let { bytes ->
            getMetadata(newFileUri, context) { fileName, size ->
                suspend {
                    if (size != null && size.toMB() <= 2.0F)
                        service.updateDocument(
                            document.id,
                            FileForm(
                                fileName!!.withoutExtension(),
                                size.toMB(),
                                bytes,
                                fileName.getExtension()
                            )
                        )
                    else throw Exception("Размер файла не может превышать 2Мб.")
                }.execute {
                    if (it is Success)
                        getAllDocuments()

                    copy(updateDocumentState = it)
                }
            }
        }

    fun familiarizeDocument(familiarize: Familiarize) = suspend {
        service.familiarizeDocument(familiarize)
    }.executeWithUpdate()

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

    fun getFile(document: Document) = suspend {
        service.getFile(document)
    }.execute { copy(file = it) }

    fun isAuthor(document: Document): Boolean = service.isMyId(document.author.id)

    companion object : MavericksViewModelFactory<DocumentsViewModel, DocumentsState>,
        KoinComponent {
        override fun create(
            viewModelContext: ViewModelContext,
            state: DocumentsState
        ): DocumentsViewModel = get { parametersOf(state) }
    }
    private fun (suspend () -> Unit).executeWithUpdate() = execute {
        if(it is Success) getAllDocuments()
        copy(loadingState = it)
    }
}

data class DocumentsState(
    val documents: Async<List<Documentable>> = Uninitialized,
    val users: Async<List<User>> = Uninitialized,
    val addingDocumentState: Async<Unit> = Uninitialized,
    val file: Async<File> = Uninitialized,
    val updateDocumentState: Async<Unit> = Uninitialized,
    val loadingState: Async<Unit> = Uninitialized
) : MavericksState