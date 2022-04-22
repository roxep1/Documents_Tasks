package com.bashkir.documentstasks.viewmodels

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import com.airbnb.mvrx.*
import com.bashkir.documentstasks.data.models.*
import com.bashkir.documentstasks.data.services.DocumentsService
import com.bashkir.documentstasks.ui.components.filters.DocumentFilterOption
import com.bashkir.documentstasks.ui.components.filters.DocumentFilterOption.*
import com.bashkir.documentstasks.utils.filter
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf
import java.io.FileInputStream
import java.io.FileOutputStream

class DocumentsViewModel(
    initialState: DocumentsState,
    private val service: DocumentsService,
    private val context: Context
) : MavericksViewModel<DocumentsState>(initialState) {

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

    fun downloadDocument(document: Document, uri: Uri?) =
        uri?.let {
            context.contentResolver.openFileDescriptor(uri, "w")?.use {
                FileOutputStream(it.fileDescriptor).use { stream ->
                    stream.write(document.file)
                }
            }
        }

    fun addDocument(document: DocumentForm) = suspend {
        service.addDocument(document)
    }.execute {
        getAllDocuments()
        copy()
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

    fun updateDocument(document: DocumentForm) = suspend {
        service.updateDocument(document)
    }.execute {
        getAllDocuments()
        copy()
    }

    fun familiarizeDocument(familiarize: Familiarize) = suspend {
        service.familiarizeDocument(familiarize)
    }.execute {
        getAllDocuments()
        copy()
    }

    fun getBytesDocument(uri: Uri?): ByteArray? =
        uri?.let {
            context.contentResolver.openFileDescriptor(uri, "r").use {
                FileInputStream(it?.fileDescriptor).use { stream ->
                    stream.readBytes()
                }
            }
        }

    fun getMetadata(uri: Uri?, onResult: (String?, Long?) -> Unit) =
        uri?.let {
            context.contentResolver.query(
                uri, null, null, null, null, null
            )?.use { cursor ->
                if (cursor.moveToFirst()) {
                    val index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    val displayName = if (index >= 0) cursor.getString(index) else null
                    val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
                    val size = if (sizeIndex >= 0) cursor.getLong(sizeIndex) else null
                    onResult(displayName, size)
                }
            }
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