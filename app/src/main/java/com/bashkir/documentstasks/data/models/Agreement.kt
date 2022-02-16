package com.bashkir.documentstasks.data.models

import java.time.LocalDateTime

data class Agreement(
    val id: Int,
    val userId: String,
    val documentId: Int,
    val deadline: LocalDateTime,
    val status: AgreementStatus,
    val created: LocalDateTime,
    val comment: String? = null,
    val statusChanged: LocalDateTime? = null
)

data class AgreementForm(
    val userId: String,
    val deadline: LocalDateTime
)