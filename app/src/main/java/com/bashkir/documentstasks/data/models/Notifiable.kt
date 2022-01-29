package com.bashkir.documentstasks.data.models

import java.time.LocalDateTime

sealed class Notifiable(
    open val title: String,
    open val author: User,
    val subject: String,
    val time: LocalDateTime,
    val destination: String,
    val checked: Boolean = false
)