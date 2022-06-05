package com.bashkir.documentstasks.data.services

import com.bashkir.documentstasks.data.models.*
import com.bashkir.documentstasks.data.repositories.localdata.room.DocumentDao
import org.koin.java.KoinJavaComponent.inject

class DocumentsService : NotificationsService() {
    private val documentDao: DocumentDao by inject(DocumentDao::class.java)

    suspend fun getAllDocuments(): List<Documentable> =
        if (isOnline)
            api.getCreatedDocuments().run {
                plus(
                    api.getAllAgreements()
                ).plus(
                    api.getAllFamiliarizes()
                )
            }.also { doLocalWork(it) }
        else documentDao.getAllLocalDocuments().map(FullLocalDocument::toDocument).run {
            plus(
                documentDao.getMyAgreements(preferences.authorizedId)
                    .map(AgreementEntity::toAgreement)
            )
                .plus(
                    documentDao.getMyFamiliarizes(preferences.authorizedId)
                        .map(FamiliarizeEntity::toFamiliarize)
                )
        }

    private suspend fun doLocalWork(documentables: List<Documentable>) {
        val documents: ArrayList<Document> = arrayListOf()
        val familiarizes: ArrayList<Familiarize> = arrayListOf()
        val agreements: ArrayList<Agreement> = arrayListOf()

        documentables.forEach { documentable ->
            when (documentable) {
                is Document -> {
                    documents.add(documentable)
                    familiarizes.addAll(documentable.familiarize)
                    agreements.addAll(documentable.agreement)
                }
                is Familiarize -> {
                    familiarizes.add(documentable)
                    documents.add(documentable.document)
                }
                is Agreement -> {
                    agreements.add(documentable)
                    documents.add(documentable.document)
                }
            }
        }
        documentDao.insertAll(
            documents.map(Document::toEntity),
            agreements.map(Agreement::toEntity),
            familiarizes.map(Familiarize::toEntity)
        )

        documentDao.deleteAllNotIn(documents.map(Document::id))
        documentDao.deleteAllAgreementsNotIn(agreements.map(Agreement::id))
        documentDao.deleteAllFamiliarizesNotIn(familiarizes.map(Familiarize::id))
    }

    suspend fun familiarizeDocument(familiarize: Familiarize) = api.familiarize(familiarize.id)

    suspend fun updateDocument(documentId: Int, file: FileForm) =
        api.updateDocument(documentId, file)

    suspend fun declineDocument(agreement: Agreement, comment: String) =
        api.agreedWithComment(agreement.id, AgreementStatus.Declined, comment)

    suspend fun declineDocument(agreement: Agreement) =
        api.agreed(agreement.id, AgreementStatus.Declined)

    suspend fun agreedDocument(agreement: Agreement, comment: String) =
        api.agreedWithComment(agreement.id, AgreementStatus.Agreed, comment)

    suspend fun agreedDocument(agreement: Agreement) =
        api.agreed(agreement.id, AgreementStatus.Agreed)

    suspend fun addDocument(document: DocumentForm, file: FileForm) =
        api.addDocument(
            DocumentWithFile(
                document.copy(author = UserForm(preferences.authorizedId)),
                file
            )
        )

    suspend fun deleteDocument(document: Document) = api.deleteDocument(document.id)
}