package com.bashkir.documentstasks.data.models

data class File(
    val id: Int,
    val name: String,
    val size: Float?,
    val file: ByteArray,
    val extension: String
) {

    fun getFileType(): String = "application/${
        when (extension) {
            "docx" -> "vnd.openxmlformats-officedocument.wordprocessingml.document"
            "doc" -> "msword"
            "xls" -> "vnd.ms-excel"
            "xlsx" -> "vnd.ms-excel"
            else -> extension
        }
    }"
}

data class FileForm(
    val name: String,
    val size: Float?,
    val file: ByteArray,
    val extension: String
)

