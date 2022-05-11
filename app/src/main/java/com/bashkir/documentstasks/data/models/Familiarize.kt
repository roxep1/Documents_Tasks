package com.bashkir.documentstasks.data.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bashkir.documentstasks.utils.LocalDateTimeJsonAdapter
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class Familiarize(
    @PrimaryKey val id: Int,
    val user: User,
    val document: Document,
    @SerializedName("familiarized")
    val checked: Boolean,

    @JsonAdapter(LocalDateTimeJsonAdapter::class)
    val created: LocalDateTime
) : Documentable {
    override fun toDocument(): Document = document

    fun toEntity(): FamiliarizeEntity =
        FamiliarizeEntity(id, user.toEntity(), document.toEntity(), checked, created)
}

data class FamiliarizeForm(
    val user: UserForm
)

@Entity(tableName = "familiarize")
data class FamiliarizeEntity(
    @PrimaryKey val id: Int,
    @Embedded val user: UserEntity,
    @Embedded(prefix = "document") val document: DocumentEntity,
    val checked: Boolean,
    val created: LocalDateTime
) {
    fun toFamiliarize(): Familiarize =
        Familiarize(id, user.toUser(), document.toDocument(), checked, created)
}
