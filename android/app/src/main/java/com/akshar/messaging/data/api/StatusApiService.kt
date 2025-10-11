package com.akshar.messaging.data.api

import com.akshar.messaging.data.models.*
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface StatusApiService {
    
    // Create a new status
    @POST("status")
    suspend fun createStatus(
        @Header("Authorization") token: String,
        @Body request: StatusRequest
    ): Response<ApiResponse>
    
    // Get all statuses for user and contacts
    @GET("status")
    suspend fun getStatuses(
        @Header("Authorization") token: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): Response<StatusListResponse>
    
    // Get statuses for a specific user
    @GET("status/user/{userId}")
    suspend fun getUserStatuses(
        @Header("Authorization") token: String,
        @Path("userId") userId: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): Response<StatusListResponse>
    
    // Mark a status as viewed
    @POST("status/{statusId}/view")
    suspend fun markStatusAsViewed(
        @Header("Authorization") token: String,
        @Path("statusId") statusId: String
    ): Response<ApiResponse>
    
    // Delete a status
    @DELETE("status/{statusId}")
    suspend fun deleteStatus(
        @Header("Authorization") token: String,
        @Path("statusId") statusId: String
    ): Response<ApiResponse>
    
    // Get status statistics
    @GET("status/stats")
    suspend fun getStatusStats(
        @Header("Authorization") token: String
    ): Response<StatusStatsResponse>
    
    // Upload audio file for voice status
    @Multipart
    @POST("status/upload-audio")
    suspend fun uploadAudio(
        @Header("Authorization") token: String,
        @Part audio: MultipartBody.Part
    ): Response<FileUploadResponse>
    
    // Upload image file for image status
    @Multipart
    @POST("status/upload-image")
    suspend fun uploadImage(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part
    ): Response<FileUploadResponse>
}
