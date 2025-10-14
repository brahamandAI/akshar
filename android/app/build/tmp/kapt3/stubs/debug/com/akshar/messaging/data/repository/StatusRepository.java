package com.akshar.messaging.data.repository;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u009e\u0001\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u00062\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\n2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00132\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u00162\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\nH\u0086@\u00a2\u0006\u0002\u0010\u0018J*\u0010\u0019\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0\u00070\u00062\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u001b\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u001cJ\"\u0010\u001d\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001e0\u00070\u00062\u0006\u0010\t\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u001fJ<\u0010 \u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0!0\u00070\u00062\u0006\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\"\u001a\u00020#2\b\b\u0002\u0010$\u001a\u00020#H\u0086@\u00a2\u0006\u0002\u0010%JD\u0010&\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0!0\u00070\u00062\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\'\u001a\u00020\n2\b\b\u0002\u0010\"\u001a\u00020#2\b\b\u0002\u0010$\u001a\u00020#H\u0086@\u00a2\u0006\u0002\u0010(J*\u0010)\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020#0\u00070\u00062\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u001b\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u001cJ*\u0010*\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u00070\u00062\u0006\u0010\t\u001a\u00020\n2\u0006\u0010+\u001a\u00020,H\u0086@\u00a2\u0006\u0002\u0010-J*\u0010.\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u00070\u00062\u0006\u0010\t\u001a\u00020\n2\u0006\u0010/\u001a\u00020,H\u0086@\u00a2\u0006\u0002\u0010-R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u00060"}, d2 = {"Lcom/akshar/messaging/data/repository/StatusRepository;", "", "statusApiService", "Lcom/akshar/messaging/data/api/StatusApiService;", "(Lcom/akshar/messaging/data/api/StatusApiService;)V", "createStatus", "Lkotlinx/coroutines/flow/Flow;", "Lkotlin/Result;", "Lcom/akshar/messaging/data/models/StatusResponse;", "token", "", "type", "content", "backgroundColor", "fontFamily", "songTitle", "artist", "duration", "template", "Lcom/akshar/messaging/data/models/LayoutTemplate;", "audioPath", "audioDuration", "", "imagePath", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/akshar/messaging/data/models/LayoutTemplate;Ljava/lang/String;Ljava/lang/Long;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteStatus", "", "statusId", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getStatusStats", "Lcom/akshar/messaging/data/models/StatusStats;", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getStatuses", "", "page", "", "limit", "(Ljava/lang/String;IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getUserStatuses", "userId", "(Ljava/lang/String;Ljava/lang/String;IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "markStatusAsViewed", "uploadAudio", "audioFile", "Ljava/io/File;", "(Ljava/lang/String;Ljava/io/File;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "uploadImage", "imageFile", "app_debug"})
public final class StatusRepository {
    @org.jetbrains.annotations.NotNull
    private final com.akshar.messaging.data.api.StatusApiService statusApiService = null;
    
    @javax.inject.Inject
    public StatusRepository(@org.jetbrains.annotations.NotNull
    com.akshar.messaging.data.api.StatusApiService statusApiService) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object createStatus(@org.jetbrains.annotations.NotNull
    java.lang.String token, @org.jetbrains.annotations.NotNull
    java.lang.String type, @org.jetbrains.annotations.NotNull
    java.lang.String content, @org.jetbrains.annotations.Nullable
    java.lang.String backgroundColor, @org.jetbrains.annotations.Nullable
    java.lang.String fontFamily, @org.jetbrains.annotations.Nullable
    java.lang.String songTitle, @org.jetbrains.annotations.Nullable
    java.lang.String artist, @org.jetbrains.annotations.Nullable
    java.lang.String duration, @org.jetbrains.annotations.Nullable
    com.akshar.messaging.data.models.LayoutTemplate template, @org.jetbrains.annotations.Nullable
    java.lang.String audioPath, @org.jetbrains.annotations.Nullable
    java.lang.Long audioDuration, @org.jetbrains.annotations.Nullable
    java.lang.String imagePath, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<kotlin.Result<com.akshar.messaging.data.models.StatusResponse>>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object getStatuses(@org.jetbrains.annotations.NotNull
    java.lang.String token, int page, int limit, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends kotlin.Result<? extends java.util.List<com.akshar.messaging.data.models.StatusResponse>>>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object getUserStatuses(@org.jetbrains.annotations.NotNull
    java.lang.String token, @org.jetbrains.annotations.NotNull
    java.lang.String userId, int page, int limit, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends kotlin.Result<? extends java.util.List<com.akshar.messaging.data.models.StatusResponse>>>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object markStatusAsViewed(@org.jetbrains.annotations.NotNull
    java.lang.String token, @org.jetbrains.annotations.NotNull
    java.lang.String statusId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<kotlin.Result<java.lang.Integer>>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object deleteStatus(@org.jetbrains.annotations.NotNull
    java.lang.String token, @org.jetbrains.annotations.NotNull
    java.lang.String statusId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<kotlin.Result<kotlin.Unit>>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object getStatusStats(@org.jetbrains.annotations.NotNull
    java.lang.String token, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<kotlin.Result<com.akshar.messaging.data.models.StatusStats>>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object uploadAudio(@org.jetbrains.annotations.NotNull
    java.lang.String token, @org.jetbrains.annotations.NotNull
    java.io.File audioFile, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<kotlin.Result<java.lang.String>>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object uploadImage(@org.jetbrains.annotations.NotNull
    java.lang.String token, @org.jetbrains.annotations.NotNull
    java.io.File imageFile, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<kotlin.Result<java.lang.String>>> $completion) {
        return null;
    }
}