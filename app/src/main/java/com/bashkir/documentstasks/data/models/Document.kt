package com.bashkir.documentstasks.data.models

import java.time.LocalDateTime

data class Document(
    val id: Int,
    val author: User,
    val title: String,
    val file: String,
    val created: LocalDateTime,
    val desc: String? = null,
    val templateId: String? = null,
    val familiarize: List<Familiarize> = listOf(),
    val agreement: List<Agreement> = listOf()
)

data class DocumentForm(
    val title: String,
    val file: String,
    val desc: String?,
    val familiarize: List<FamiliarizeForm>,
    val agreement: List<AgreementForm>,
    val author: UserForm? = null,
    val templateId: String? = null
)