package com.bashkir.documentstasks.data.models
import androidx.annotation.Nullable
import androidx.room.*
import com.bashkir.documentstasks.utils.LocalDateTimeJsonAdapter
import com.google.gson.annotations.JsonAdapter
import java.time.LocalDateTime

data class Document(
    val id: Int,
    val author: User,
    val title: String,
    val file: ByteArray,

    @JsonAdapter(LocalDateTimeJsonAdapter::class)
    val created: LocalDateTime,
    val extension: String,
    val desc: String? = null,
    val templateId: String? = null,
    val familiarize: List<Familiarize> = listOf(),
    val agreement: List<Agreement> = listOf()
) : Documentable {
    override fun toDocument(): Document = this

    fun toEntity(): DocumentEntity =
        DocumentEntity(id, author.toEntity(), title, file, created, extension, desc, templateId)

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

data class DocumentForm(
    val title: String,
    val file: ByteArray,
    val extension: String,
    val desc: String?,
    val familiarize: List<FamiliarizeForm>,
    val agreement: List<AgreementForm>,
    val author: UserForm? = null,
    val templateId: String? = null,
    val id: Int? = null
)

@Entity(tableName = "document")
data class DocumentEntity(
    @PrimaryKey val id: Int,
    @Embedded val author: UserEntity,
    val title: String,
    val file: ByteArray,
    val created: LocalDateTime,
    val extension: String,
    @Nullable val desc: String?,
    val templateId: String?
) {
    fun toDocument(): Document =
        Document(id, author.toUser(), title, file, created, extension, desc, templateId)
}

data class FullLocalDocument(
    @Embedded val document: DocumentEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "documentid"
    )
    val agreements: List<AgreementEntity>,

    @Relation(
        parentColumn = "id",
        entityColumn = "documentid"
    )
    val familiarizes: List<FamiliarizeEntity>
) {
    fun toDocument(): Document = document.toDocument().run {
        Document(
            id,
            author,
            title,
            file,
            created,
            extension,
            desc,
            templateId,
            familiarizes.map { it.toFamiliarize()},
            agreements.map { it.toAgreement() }
        )
    }
}