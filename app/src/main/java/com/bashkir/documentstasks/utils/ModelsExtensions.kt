package com.bashkir.documentstasks.utils

import com.bashkir.documentstasks.data.models.*

fun List<Task>.getAllPerforms(): List<Perform> = map { it.performs }
    .fold(listOf()) { acc, list -> acc.plus(list) }

fun List<User>.toFamiliarizeForms(): List<FamiliarizeForm> = map { FamiliarizeForm(it.toForm()) }