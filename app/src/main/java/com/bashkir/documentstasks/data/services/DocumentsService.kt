package com.bashkir.documentstasks.data.services

import com.bashkir.documentstasks.data.models.*

class DocumentsService : NotificationsService() {

    suspend fun getAllDocuments(): List<Documentable> =
        api.getCreatedDocuments(preferences.authorizedId).run {
            plus(
                api.getAllAgreements(preferences.authorizedId)
            ).plus(
                api.getAllFamiliarizes(preferences.authorizedId)
            )
        }

    suspend fun familiarizeDocument(familiarize: Familiarize) = api.familiarize(familiarize.id)

    suspend fun updateDocument(document: DocumentForm) =
        api.updateDocument(document)

    suspend fun declineDocument(agreement: Agreement, comment: String) =
        api.agreedWithComment(agreement.id, AgreementStatus.Declined, comment)

    suspend fun declineDocument(agreement: Agreement) =
        api.agreed(agreement.id, AgreementStatus.Declined)

    suspend fun agreedDocument(agreement: Agreement, comment: String) =
        api.agreedWithComment(agreement.id, AgreementStatus.Agreed, comment)

    suspend fun agreedDocument(agreement: Agreement) =
        api.agreed(agreement.id, AgreementStatus.Agreed)

    suspend fun addDocument(document: DocumentForm) =
        api.addDocument(document.copy(author = UserForm(preferences.authorizedId)))
}