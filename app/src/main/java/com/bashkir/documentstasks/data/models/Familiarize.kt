package com.bashkir.documentstasks.data.models

import com.bashkir.documentstasks.utils.LocalDateTimeJsonAdapter
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class Familiarize(
    val id: Int,
    val user: User,
    val document: Document,
    @SerializedName("familiarized")
    val checked: Boolean,

    @JsonAdapter(LocalDateTimeJsonAdapter::class)
    val created: LocalDateTime
): Documentable{
    override fun toDocument(): Document = document
}

data class FamiliarizeForm(
    val user: UserForm
)
