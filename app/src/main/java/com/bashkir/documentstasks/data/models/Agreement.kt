package com.bashkir.documentstasks.data.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bashkir.documentstasks.utils.LocalDateTimeJsonAdapter
import com.google.gson.annotations.JsonAdapter
import java.time.LocalDateTime

data class Agreement(
    val id: Int,
    val user: User,
    val document: Document,

    @JsonAdapter(LocalDateTimeJsonAdapter::class)
    val deadline: LocalDateTime,
    val status: AgreementStatus,

    @JsonAdapter(LocalDateTimeJsonAdapter::class)
    val created: LocalDateTime,
    val comment: String? = null,

    @JsonAdapter(LocalDateTimeJsonAdapter::class)
    val statusChanged: LocalDateTime? = null
) : Documentable {
    override fun toDocument(): Document = document

    fun toEntity(): AgreementEntity = AgreementEntity(
        id,
        user.toEntity(),
        document.toEntity(),
        deadline,
        status,
        created,
        comment,
        statusChanged
    )
}

data class AgreementForm(
    val user: UserForm,

    @JsonAdapter(LocalDateTimeJsonAdapter::class)
    val deadline: LocalDateTime
)

@Entity(tableName = "agreement")
data class AgreementEntity(
    @PrimaryKey val id: Int,
    @Embedded val user: UserEntity,
    @Embedded(prefix = "document") val document: DocumentEntity,
    val deadline: LocalDateTime,
    val status: AgreementStatus,
    val created: LocalDateTime,
    val comment: String? = null,
    val statusChanged: LocalDateTime? = null
) {
    fun toAgreement(): Agreement =
        Agreement(
            id,
            user.toUser(),
            document.toDocument(),
            deadline,
            status,
            created,
            comment,
            statusChanged
        )
}