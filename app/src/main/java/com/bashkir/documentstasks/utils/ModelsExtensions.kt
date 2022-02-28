package com.bashkir.documentstasks.utils

import com.bashkir.documentstasks.data.models.Perform
import com.bashkir.documentstasks.data.models.Task

fun List<Task>.getAllPerforms(): List<Perform> = map { it.performs }
    .fold(listOf<Perform>()) { acc, list -> acc.plus(list) }