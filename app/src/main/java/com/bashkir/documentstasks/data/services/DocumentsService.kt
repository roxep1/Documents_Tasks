package com.bashkir.documentstasks.data.services

import com.bashkir.documentstasks.data.models.Documentable

class DocumentsService : NotificationsService() {

    suspend fun getAllDocuments(): List<Documentable> =
        api.getCreatedDocuments(preferences.authorizedId).run {
            plus(
                api.getAllAgreements(preferences.authorizedId)
            ).plus(
                api.getAllFamiliarizes(preferences.authorizedId)
            )
        }
}