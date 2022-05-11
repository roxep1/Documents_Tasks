package com.bashkir.documentstasks.data.repositories.localdata.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import com.bashkir.documentstasks.data.models.AgreementEntity
import com.bashkir.documentstasks.data.models.DocumentEntity
import com.bashkir.documentstasks.data.models.FamiliarizeEntity
import com.bashkir.documentstasks.data.models.FullLocalDocument

@Dao
interface DocumentDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(
        documents: List<DocumentEntity>,
        agreements: List<AgreementEntity>,
        familiarizes: List<FamiliarizeEntity>
    )

    @Transaction
    @Query("SELECT * FROM document")
    suspend fun getAllLocalDocuments(): List<FullLocalDocument>

    @Query("SELECT * FROM familiarize WHERE userId == :userId")
    suspend fun getMyFamiliarizes(userId: String): List<FamiliarizeEntity>

    @Query("SELECT * FROM agreement WHERE userId == :userId")
    suspend fun getMyAgreements(userId: String): List<AgreementEntity>

    @Query("DELETE FROM document WHERE id NOT IN (:documentsId) ")
    suspend fun deleteAllNotIn(documentsId: List<Int>)

    @Query("DELETE FROM familiarize WHERE id NOT IN (:familiarizesId) ")
    suspend fun deleteAllFamiliarizesNotIn(familiarizesId: List<Int>)

    @Query("DELETE FROM agreement WHERE id NOT IN (:agreementsId) ")
    suspend fun deleteAllAgreementsNotIn(agreementsId: List<Int>)
}