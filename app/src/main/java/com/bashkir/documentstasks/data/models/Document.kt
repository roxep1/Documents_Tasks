package com.bashkir.documentstasks.data.models

import java.time.LocalDateTime

data class Document(
    val id: Int,
    val author: User,
    val title: String,
    val file: ByteArray,
    val created: LocalDateTime,
    val desc: String? = null,
    val templateId: String? = null,
    val familiarize: List<Familiarize> = listOf(),
    val agreement: List<Agreement> = listOf()
): Documentable {
    override fun toDocument(): Document = this

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Document

        if (!file.contentEquals(other.file)) return false

        return true
    }

    override fun hashCode(): Int {
        return file.contentHashCode()
    }
}

data class DocumentForm(
    val title: String,
    val file: ByteArray,
    val desc: String?,
    val familiarize: List<FamiliarizeForm>,
    val agreement: List<AgreementForm>,
    val author: UserForm? = null,
    val templateId: String? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DocumentForm

        if (!file.contentEquals(other.file)) return false

        return true
    }

    override fun hashCode(): Int {
        return file.contentHashCode()
    }
}