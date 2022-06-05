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

    @JsonAdapter(LocalDateTimeJsonAdapter::class)
    val created: LocalDateTime,
    val desc: String? = null,
    val templateId: String? = null,
    val taskId: Int? = null,
    val familiarize: List<Familiarize> = listOf(),
    val agreement: List<Agreement> = listOf()
) : Documentable {
    override fun toDocument(): Document = this

    fun toEntity(): DocumentEntity =
        DocumentEntity(id, author.toEntity(), title, created, desc, templateId, taskId)
}

data class DocumentForm(
    val title: String,
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
    val created: LocalDateTime,
    @Nullable val desc: String?,
    val templateId: String?,
    @Nullable val taskId: Int?
) {
    fun toDocument(): Document =
        Document(id, author.toUser(), title, created, desc, templateId)
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
            created,
            desc,
            templateId,
            taskId,
            familiarizes.map { it.toFamiliarize()},
            agreements.map { it.toAgreement() }
        )
    }
}