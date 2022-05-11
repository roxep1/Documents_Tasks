package com.bashkir.documentstasks.utils

fun Long.toMB(): String = "${div(1000000)},${
    (this % 1000000).toString().let { string ->
        string.substring(0..1).let { cutString ->
            if (string.last().digitToInt() > 4)
                cutString.toLong().inc()
            else cutString
        }
    }
} Мб"

fun String.getExtension(): String = substring(lastIndexOf(".") + 1)
