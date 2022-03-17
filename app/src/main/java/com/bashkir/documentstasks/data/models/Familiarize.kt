package com.bashkir.documentstasks.data.models

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class Familiarize(
    val id: Int,
    val user: User,
    val document: Document,
    @SerializedName("familiarized")
    val checked: Boolean,
    val created: LocalDateTime
): Documentable{
    override fun toDocument(): Document = document
}

data class FamiliarizeForm(
    val user: UserForm
)
