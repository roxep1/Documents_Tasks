package com.bashkir.documentstasks.data.models

import java.time.LocalDateTime

data class Perform(
    val id: Int,
    val user: User,
    val taskId: Int,
    val status: PerformStatus,
    val comment: String? = null,
    val statusChanged: LocalDateTime? = null,
    val documents: List<Document> = listOf()
)

data class PerformForm(
    val user: UserForm
)
