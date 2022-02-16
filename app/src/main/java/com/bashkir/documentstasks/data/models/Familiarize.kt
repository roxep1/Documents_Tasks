package com.bashkir.documentstasks.data.models

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class Familiarize(
    val id: Int,
    val userId: String,
    val documentId: Int,
    @SerializedName("familiarized")
    val checked: Boolean,
    val created: LocalDateTime
)

data class FamiliarizeForm(
    val userId: String
)
