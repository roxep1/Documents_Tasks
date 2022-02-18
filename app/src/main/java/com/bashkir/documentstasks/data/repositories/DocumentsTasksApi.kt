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

    @PUT("task/status")
    suspend fun inProgressAllPerforms(@Body performsId: List<Int>)
}