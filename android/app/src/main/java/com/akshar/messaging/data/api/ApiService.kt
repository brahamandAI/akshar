package com.akshar.messaging.data.api

import com.akshar.messaging.data.models.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    
    // Authentication Endpoints
    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>
    
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>
    
    @POST("auth/logout")
    suspend fun logout(): Response<ApiResponse>
    
    // User Endpoints
    @GET("users/profile")
    suspend fun getProfile(): Response<UserResponse>
    
    @PUT("users/profile")
    suspend fun updateProfile(@Body request: UpdateProfileRequest): Response<UserResponse>
    
    @Multipart
    @POST("users/upload-avatar")
    suspend fun uploadAvatar(@Part avatar: okhttp3.MultipartBody.Part): Response<UserResponse>
    
    @GET("users/search")
    suspend fun searchUsers(@Query("q") query: String): Response<UsersResponse>
    
    // Chat Endpoints
    @POST("chats")
    suspend fun createChat(@Body request: CreateChatRequest): Response<ChatResponse>
    
    @POST("chats/group")
    suspend fun createGroup(@Body request: CreateGroupRequest): Response<ChatResponse>
    
    @GET("chats")
    suspend fun getUserChats(@Query("includeArchived") includeArchived: Boolean = false): Response<ChatsResponse>
    
    @GET("chats/{chatId}/messages")
    suspend fun getChatMessages(@Path("chatId") chatId: String): Response<MessagesResponse>
    
    @PUT("chats/{chatId}/archive")
    suspend fun archiveChat(@Path("chatId") chatId: String): Response<ApiResponse>
    
    @PUT("chats/{chatId}/unarchive")
    suspend fun unarchiveChat(@Path("chatId") chatId: String): Response<ApiResponse>
    
    // Message Endpoints
    @POST("messages/{chatId}")
    suspend fun sendMessage(@Path("chatId") chatId: String, @Body request: SendMessageRequest): Response<MessageResponse>
    
    @GET("messages/{messageId}")
    suspend fun getMessage(@Path("messageId") messageId: String): Response<MessageResponse>
    
    @DELETE("messages/{messageId}")
    suspend fun deleteMessage(@Path("messageId") messageId: String): Response<ApiResponse>
    
    // File Upload
    @Multipart
    @POST("messages/upload")
    suspend fun uploadFile(
        @Part("file") file: okhttp3.MultipartBody.Part,
        @Part("chatId") chatId: String
    ): Response<UploadResponse>
    
    // Status/Stories Endpoints
    @POST("status")
    suspend fun createStatus(@Body request: com.akshar.messaging.data.models.CreateStatusRequest): Response<com.akshar.messaging.data.models.StatusResponse>
    
    @GET("status")
    suspend fun getStatuses(): Response<com.akshar.messaging.data.models.StatusesResponse>
    
    @GET("status/user/{userId}")
    suspend fun getUserStatuses(@Path("userId") userId: String): Response<com.akshar.messaging.data.models.StatusesResponse>
    
    @POST("status/{statusId}/view")
    suspend fun viewStatus(@Path("statusId") statusId: String): Response<ApiResponse>
    
    @DELETE("status/{statusId}")
    suspend fun deleteStatus(@Path("statusId") statusId: String): Response<ApiResponse>
}

