package com.bashkir.documentstasks.data.models

import java.time.LocalDateTime

data class Agreement(
    val id: Int,
    val user: User,
    val document: Document,
    val deadline: LocalDateTime,
    val status: AgreementStatus,
    val created: LocalDateTime,
    val comment: String? = null,
    val statusChanged: LocalDateTime? = null
): Documentable{
    override fun toDocument(): Document = document
}

data class AgreementForm(
    val user: UserForm,
    val deadline: LocalDateTime
)