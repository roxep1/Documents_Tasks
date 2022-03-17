package com.bashkir.documentstasks.data.models

sealed interface Documentable{
    fun toDocument(): Document
}
