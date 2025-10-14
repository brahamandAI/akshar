package com.akshar.messaging.data.api;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u00a2\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J\u001e\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0007J\u001e\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u00032\b\b\u0001\u0010\n\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\fJ\u001e\u0010\r\u001a\b\u0012\u0004\u0012\u00020\t0\u00032\b\b\u0001\u0010\n\u001a\u00020\u000eH\u00a7@\u00a2\u0006\u0002\u0010\u000fJ\u001e\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u00032\b\b\u0001\u0010\n\u001a\u00020\u0012H\u00a7@\u00a2\u0006\u0002\u0010\u0013J\u001e\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0015\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0007J\u001e\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0017\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0007J\u001e\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0007J\u001e\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\u00032\b\b\u0001\u0010\u0015\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0007J\u0014\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u001eJ\u0014\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020 0\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u001eJ\u001e\u0010!\u001a\b\u0012\u0004\u0012\u00020\"0\u00032\b\b\u0003\u0010#\u001a\u00020$H\u00a7@\u00a2\u0006\u0002\u0010%J\u001e\u0010&\u001a\b\u0012\u0004\u0012\u00020 0\u00032\b\b\u0001\u0010\'\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0007J\u001e\u0010(\u001a\b\u0012\u0004\u0012\u00020)0\u00032\b\b\u0001\u0010\n\u001a\u00020*H\u00a7@\u00a2\u0006\u0002\u0010+J\u0014\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u001eJ\u001e\u0010-\u001a\b\u0012\u0004\u0012\u00020)0\u00032\b\b\u0001\u0010\n\u001a\u00020.H\u00a7@\u00a2\u0006\u0002\u0010/J\u001e\u00100\u001a\b\u0012\u0004\u0012\u0002010\u00032\b\b\u0001\u00102\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0007J(\u00103\u001a\b\u0012\u0004\u0012\u00020\u001b0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\n\u001a\u000204H\u00a7@\u00a2\u0006\u0002\u00105J\u001e\u00106\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0007J\u001e\u00107\u001a\b\u0012\u0004\u0012\u00020\u001d0\u00032\b\b\u0001\u0010\n\u001a\u000208H\u00a7@\u00a2\u0006\u0002\u00109J\u001e\u0010:\u001a\b\u0012\u0004\u0012\u00020\u001d0\u00032\b\b\u0001\u0010;\u001a\u00020<H\u00a7@\u00a2\u0006\u0002\u0010=J(\u0010>\u001a\b\u0012\u0004\u0012\u00020?0\u00032\b\b\u0001\u0010@\u001a\u00020<2\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010AJ\u001e\u0010B\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0017\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0007\u00a8\u0006C"}, d2 = {"Lcom/akshar/messaging/data/api/ApiService;", "", "archiveChat", "Lretrofit2/Response;", "Lcom/akshar/messaging/data/models/ApiResponse;", "chatId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createChat", "Lcom/akshar/messaging/data/models/ChatResponse;", "request", "Lcom/akshar/messaging/data/models/CreateChatRequest;", "(Lcom/akshar/messaging/data/models/CreateChatRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createGroup", "Lcom/akshar/messaging/data/models/CreateGroupRequest;", "(Lcom/akshar/messaging/data/models/CreateGroupRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createStatus", "Lcom/akshar/messaging/data/models/StatusResponse;", "Lcom/akshar/messaging/data/models/CreateStatusRequest;", "(Lcom/akshar/messaging/data/models/CreateStatusRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteMessage", "messageId", "deleteStatus", "statusId", "getChatMessages", "Lcom/akshar/messaging/data/models/MessagesResponse;", "getMessage", "Lcom/akshar/messaging/data/models/MessageResponse;", "getProfile", "Lcom/akshar/messaging/data/models/UserResponse;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getStatuses", "Lcom/akshar/messaging/data/models/StatusesResponse;", "getUserChats", "Lcom/akshar/messaging/data/models/ChatsResponse;", "includeArchived", "", "(ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getUserStatuses", "userId", "login", "Lcom/akshar/messaging/data/models/AuthResponse;", "Lcom/akshar/messaging/data/models/LoginRequest;", "(Lcom/akshar/messaging/data/models/LoginRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "logout", "register", "Lcom/akshar/messaging/data/models/RegisterRequest;", "(Lcom/akshar/messaging/data/models/RegisterRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "searchUsers", "Lcom/akshar/messaging/data/models/UsersResponse;", "query", "sendMessage", "Lcom/akshar/messaging/data/models/SendMessageRequest;", "(Ljava/lang/String;Lcom/akshar/messaging/data/models/SendMessageRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "unarchiveChat", "updateProfile", "Lcom/akshar/messaging/data/models/UpdateProfileRequest;", "(Lcom/akshar/messaging/data/models/UpdateProfileRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "uploadAvatar", "avatar", "Lokhttp3/MultipartBody$Part;", "(Lokhttp3/MultipartBody$Part;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "uploadFile", "Lcom/akshar/messaging/data/models/UploadResponse;", "file", "(Lokhttp3/MultipartBody$Part;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "viewStatus", "app_debug"})
public abstract interface ApiService {
    
    @retrofit2.http.POST(value = "auth/register")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object register(@retrofit2.http.Body
    @org.jetbrains.annotations.NotNull
    com.akshar.messaging.data.models.RegisterRequest request, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.akshar.messaging.data.models.AuthResponse>> $completion);
    
    @retrofit2.http.POST(value = "auth/login")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object login(@retrofit2.http.Body
    @org.jetbrains.annotations.NotNull
    com.akshar.messaging.data.models.LoginRequest request, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.akshar.messaging.data.models.AuthResponse>> $completion);
    
    @retrofit2.http.POST(value = "auth/logout")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object logout(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.akshar.messaging.data.models.ApiResponse>> $completion);
    
    @retrofit2.http.GET(value = "users/profile")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getProfile(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.akshar.messaging.data.models.UserResponse>> $completion);
    
    @retrofit2.http.PUT(value = "users/profile")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateProfile(@retrofit2.http.Body
    @org.jetbrains.annotations.NotNull
    com.akshar.messaging.data.models.UpdateProfileRequest request, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.akshar.messaging.data.models.UserResponse>> $completion);
    
    @retrofit2.http.Multipart
    @retrofit2.http.POST(value = "users/upload-avatar")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object uploadAvatar(@retrofit2.http.Part
    @org.jetbrains.annotations.NotNull
    okhttp3.MultipartBody.Part avatar, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.akshar.messaging.data.models.UserResponse>> $completion);
    
    @retrofit2.http.GET(value = "users/search")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object searchUsers(@retrofit2.http.Query(value = "q")
    @org.jetbrains.annotations.NotNull
    java.lang.String query, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.akshar.messaging.data.models.UsersResponse>> $completion);
    
    @retrofit2.http.POST(value = "chats")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object createChat(@retrofit2.http.Body
    @org.jetbrains.annotations.NotNull
    com.akshar.messaging.data.models.CreateChatRequest request, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.akshar.messaging.data.models.ChatResponse>> $completion);
    
    @retrofit2.http.POST(value = "chats/group")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object createGroup(@retrofit2.http.Body
    @org.jetbrains.annotations.NotNull
    com.akshar.messaging.data.models.CreateGroupRequest request, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.akshar.messaging.data.models.ChatResponse>> $completion);
    
    @retrofit2.http.GET(value = "chats")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getUserChats(@retrofit2.http.Query(value = "includeArchived")
    boolean includeArchived, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.akshar.messaging.data.models.ChatsResponse>> $completion);
    
    @retrofit2.http.GET(value = "chats/{chatId}/messages")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getChatMessages(@retrofit2.http.Path(value = "chatId")
    @org.jetbrains.annotations.NotNull
    java.lang.String chatId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.akshar.messaging.data.models.MessagesResponse>> $completion);
    
    @retrofit2.http.PUT(value = "chats/{chatId}/archive")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object archiveChat(@retrofit2.http.Path(value = "chatId")
    @org.jetbrains.annotations.NotNull
    java.lang.String chatId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.akshar.messaging.data.models.ApiResponse>> $completion);
    
    @retrofit2.http.PUT(value = "chats/{chatId}/unarchive")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object unarchiveChat(@retrofit2.http.Path(value = "chatId")
    @org.jetbrains.annotations.NotNull
    java.lang.String chatId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.akshar.messaging.data.models.ApiResponse>> $completion);
    
    @retrofit2.http.POST(value = "messages/{chatId}")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object sendMessage(@retrofit2.http.Path(value = "chatId")
    @org.jetbrains.annotations.NotNull
    java.lang.String chatId, @retrofit2.http.Body
    @org.jetbrains.annotations.NotNull
    com.akshar.messaging.data.models.SendMessageRequest request, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.akshar.messaging.data.models.MessageResponse>> $completion);
    
    @retrofit2.http.GET(value = "messages/{messageId}")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getMessage(@retrofit2.http.Path(value = "messageId")
    @org.jetbrains.annotations.NotNull
    java.lang.String messageId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.akshar.messaging.data.models.MessageResponse>> $completion);
    
    @retrofit2.http.DELETE(value = "messages/{messageId}")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteMessage(@retrofit2.http.Path(value = "messageId")
    @org.jetbrains.annotations.NotNull
    java.lang.String messageId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.akshar.messaging.data.models.ApiResponse>> $completion);
    
    @retrofit2.http.Multipart
    @retrofit2.http.POST(value = "messages/upload")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object uploadFile(@retrofit2.http.Part(value = "file")
    @org.jetbrains.annotations.NotNull
    okhttp3.MultipartBody.Part file, @retrofit2.http.Part(value = "chatId")
    @org.jetbrains.annotations.NotNull
    java.lang.String chatId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.akshar.messaging.data.models.UploadResponse>> $completion);
    
    @retrofit2.http.POST(value = "status")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object createStatus(@retrofit2.http.Body
    @org.jetbrains.annotations.NotNull
    com.akshar.messaging.data.models.CreateStatusRequest request, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.akshar.messaging.data.models.StatusResponse>> $completion);
    
    @retrofit2.http.GET(value = "status")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getStatuses(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.akshar.messaging.data.models.StatusesResponse>> $completion);
    
    @retrofit2.http.GET(value = "status/user/{userId}")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getUserStatuses(@retrofit2.http.Path(value = "userId")
    @org.jetbrains.annotations.NotNull
    java.lang.String userId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.akshar.messaging.data.models.StatusesResponse>> $completion);
    
    @retrofit2.http.POST(value = "status/{statusId}/view")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object viewStatus(@retrofit2.http.Path(value = "statusId")
    @org.jetbrains.annotations.NotNull
    java.lang.String statusId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.akshar.messaging.data.models.ApiResponse>> $completion);
    
    @retrofit2.http.DELETE(value = "status/{statusId}")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteStatus(@retrofit2.http.Path(value = "statusId")
    @org.jetbrains.annotations.NotNull
    java.lang.String statusId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.akshar.messaging.data.models.ApiResponse>> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}