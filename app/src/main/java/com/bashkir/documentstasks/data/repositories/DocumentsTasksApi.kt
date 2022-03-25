package com.bashkir.documentstasks.data.repositories

import com.bashkir.documentstasks.data.models.*
import retrofit2.http.*

interface DocumentsTasksApi {
    @GET("user/{id}")
    suspend fun getUser(@Path("id") userId: String): User

    @GET("users")
    suspend fun getUsers(): List<User>

    @GET("user/{id}/tasks")
    suspend fun getAllTasks(@Path("id") userId: String): List<Task>

    @GET("user/{id}/documents")
    suspend fun getCreatedDocuments(@Path("id") userId: String): List<Document>

    @GET("user/{id}/familiarizes")
    suspend fun getAllFamiliarizes(@Path("id") userId: String): List<Familiarize>

    @GET("user/{id}/agreements")
    suspend fun getAllAgreements(@Path("id") userId: String): List<Agreement>

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
    suspend fun addDocument(@Body document: DocumentForm)

    @POST("task")
    suspend fun addTask(@Body task: TaskForm)

    @DELETE("task/{id}")
    suspend fun deleteTask(@Path("id") taskId: Int)

    @PUT("task/{id}/comment")
    suspend fun addCommentToPerform(@Path("id") performId: Int, @Body comment: String)

    @PUT("task/{id}/status")
    suspend fun changePerformStatus(@Path("id") performId: Int, @Body performStatus: PerformStatus)

    @POST("document/perform/{id}")
    suspend fun addDocumentToPerform(@Path("id") performId: Int, @Body document: DocumentForm)
}