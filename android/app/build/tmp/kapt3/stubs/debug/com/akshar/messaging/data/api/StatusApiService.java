package com.akshar.messaging.data.api;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J(\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u0007\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\tJ(\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u000b\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\fJ\u001e\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u000fJ2\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0003\u0010\u0012\u001a\u00020\u00132\b\b\u0003\u0010\u0014\u001a\u00020\u0013H\u00a7@\u00a2\u0006\u0002\u0010\u0015J<\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00110\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u0017\u001a\u00020\u00062\b\b\u0003\u0010\u0012\u001a\u00020\u00132\b\b\u0003\u0010\u0014\u001a\u00020\u0013H\u00a7@\u00a2\u0006\u0002\u0010\u0018J(\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u000b\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\fJ(\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u001c\u001a\u00020\u001dH\u00a7@\u00a2\u0006\u0002\u0010\u001eJ(\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u001b0\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010 \u001a\u00020\u001dH\u00a7@\u00a2\u0006\u0002\u0010\u001e\u00a8\u0006!"}, d2 = {"Lcom/akshar/messaging/data/api/StatusApiService;", "", "createStatus", "Lretrofit2/Response;", "Lcom/akshar/messaging/data/models/ApiResponse;", "token", "", "request", "Lcom/akshar/messaging/data/models/StatusRequest;", "(Ljava/lang/String;Lcom/akshar/messaging/data/models/StatusRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteStatus", "statusId", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getStatusStats", "Lcom/akshar/messaging/data/models/StatusStatsResponse;", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getStatuses", "Lcom/akshar/messaging/data/models/StatusListResponse;", "page", "", "limit", "(Ljava/lang/String;IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getUserStatuses", "userId", "(Ljava/lang/String;Ljava/lang/String;IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "markStatusAsViewed", "uploadAudio", "Lcom/akshar/messaging/data/models/FileUploadResponse;", "audio", "Lokhttp3/MultipartBody$Part;", "(Ljava/lang/String;Lokhttp3/MultipartBody$Part;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "uploadImage", "image", "app_debug"})
public abstract interface StatusApiService {
    
    @retrofit2.http.POST(value = "status")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object createStatus(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull
    java.lang.String token, @retrofit2.http.Body
    @org.jetbrains.annotations.NotNull
    com.akshar.messaging.data.models.StatusRequest request, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.akshar.messaging.data.models.ApiResponse>> $completion);
    
    @retrofit2.http.GET(value = "status")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getStatuses(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull
    java.lang.String token, @retrofit2.http.Query(value = "page")
    int page, @retrofit2.http.Query(value = "limit")
    int limit, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.akshar.messaging.data.models.StatusListResponse>> $completion);
    
    @retrofit2.http.GET(value = "status/user/{userId}")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getUserStatuses(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull
    java.lang.String token, @retrofit2.http.Path(value = "userId")
    @org.jetbrains.annotations.NotNull
    java.lang.String userId, @retrofit2.http.Query(value = "page")
    int page, @retrofit2.http.Query(value = "limit")
    int limit, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.akshar.messaging.data.models.StatusListResponse>> $completion);
    
    @retrofit2.http.POST(value = "status/{statusId}/view")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object markStatusAsViewed(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull
    java.lang.String token, @retrofit2.http.Path(value = "statusId")
    @org.jetbrains.annotations.NotNull
    java.lang.String statusId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.akshar.messaging.data.models.ApiResponse>> $completion);
    
    @retrofit2.http.DELETE(value = "status/{statusId}")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteStatus(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull
    java.lang.String token, @retrofit2.http.Path(value = "statusId")
    @org.jetbrains.annotations.NotNull
    java.lang.String statusId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.akshar.messaging.data.models.ApiResponse>> $completion);
    
    @retrofit2.http.GET(value = "status/stats")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getStatusStats(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull
    java.lang.String token, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.akshar.messaging.data.models.StatusStatsResponse>> $completion);
    
    @retrofit2.http.Multipart
    @retrofit2.http.POST(value = "status/upload-audio")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object uploadAudio(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull
    java.lang.String token, @retrofit2.http.Part
    @org.jetbrains.annotations.NotNull
    okhttp3.MultipartBody.Part audio, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.akshar.messaging.data.models.FileUploadResponse>> $completion);
    
    @retrofit2.http.Multipart
    @retrofit2.http.POST(value = "status/upload-image")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object uploadImage(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull
    java.lang.String token, @retrofit2.http.Part
    @org.jetbrains.annotations.NotNull
    okhttp3.MultipartBody.Part image, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.akshar.messaging.data.models.FileUploadResponse>> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}