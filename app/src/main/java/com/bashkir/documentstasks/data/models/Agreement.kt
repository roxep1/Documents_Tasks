package com.bashkir.documentstasks.data.models

import com.bashkir.documentstasks.utils.LocalDateTimeJsonAdapter
import com.google.gson.annotations.JsonAdapter
import java.time.LocalDateTime

data class Agreement(
    val id: Int,
    val user: User,
    val document: Document,

    @JsonAdapter(LocalDateTimeJsonAdapter::class)
    val deadline: LocalDateTime,
    val status: AgreementStatus,

    @JsonAdapter(LocalDateTimeJsonAdapter::class)
    val created: LocalDateTime,
    val comment: String? = null,

    @JsonAdapter(LocalDateTimeJsonAdapter::class)
    val statusChanged: LocalDateTime? = null
): Documentable{
    override fun toDocument(): Document = document
}

data class AgreementForm(
    val user: UserForm,
    val deadline: LocalDateTime
)