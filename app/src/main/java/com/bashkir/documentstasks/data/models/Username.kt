package com.bashkir.documentstasks.data.models

data class Username(
    val name: String,
    val secondName : String,
    val middleName : String? = null,
    val fullName : String = "$secondName $name ${middleName?:""}"
)
