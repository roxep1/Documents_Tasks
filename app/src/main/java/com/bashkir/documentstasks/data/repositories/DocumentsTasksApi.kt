package com.bashkir.documentstasks.data.repositories

import com.bashkir.documentstasks.data.models.*
import retrofit2.Response
import retrofit2.http.*

interface DocumentsTasksApi {

    @GET("users")
    suspend fun getUsers(): List<User>

    @GET("user/tasks")
    suspend fun getAllTasks(): List<Task>

    @GET("user/documents")
    suspend fun getCreatedDocuments(): List<Document>

    @GET("user/familiarizes")
    suspend fun getAllFamiliarizes(): List<Familiarize>

    @GET("user/agreements")
    suspend fun getAllAgreements(): List<Agreement>

    @PUT("document/familiarize/{id}")
    suspend fun familiarize(@Path("id") familiarizeId: Int)

    @PUT("document/agreement/{id}")
    suspend fun agreed(@Path("id") agreementId: Int, @Body agreementStatus: AgreementStatus)

    @PUT("document/agreement/{id}")
    suspend fun agreedWithComment(
        @Path("id") agreementId: Int,
        @Body agreementStatus: AgreementStatus,
        @Query("comment") comment: String
    )

    @POST("document")
    suspend fun addDocument(@Body document: DocumentWithFile)

    @POST("task")
    suspend fun addTask(@Body task: TaskWithFiles)

    @DELETE("task/{id}")
    suspend fun deleteTask(@Path("id") taskId: Int)

    @PUT("task/{id}/comment")
    suspend fun addCommentToPerform(@Path("id") performId: Int, @Body comment: String)

    @PUT("task/{id}/status")
    suspend fun changePerformStatus(@Path("id") performId: Int, @Body performStatus: PerformStatus)

    @POST("document/perform/{id}")
    suspend fun addDocumentToPerform(
        @Path("id") performId: Int,
        @Body document: DocumentWithFile
    )

    @PUT("document/{id}")
    suspend fun updateDocument(@Path("id") documentId: Int, @Body file: FileForm)

    @FormUrlEncoded
    @POST("login")
    suspend fun login(@Field("idToken") token: String): User

    @GET("document/{id}/file")
    suspend fun getFile(@Path("id") documentId: Int): File

    @GET("logout")
    suspend fun logout()

    @DELETE("document/{id}")
    suspend fun deleteDocument(@Path("id") documentId: Int)
}