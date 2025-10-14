package com.akshar.messaging.data.models;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\b\n\u0002\b\u0018\b\u0086\b\u0018\u00002\u00020\u0001B?\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\u0010\b\u0002\u0010\b\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\t\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b\u00a2\u0006\u0002\u0010\fJ\t\u0010\u0018\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\u0005H\u00c6\u0003J\u000b\u0010\u001a\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\u0011\u0010\u001b\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\tH\u00c6\u0003J\u0010\u0010\u001c\u001a\u0004\u0018\u00010\u000bH\u00c6\u0003\u00a2\u0006\u0002\u0010\u0016JL\u0010\u001d\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0010\b\u0002\u0010\b\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\t2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000bH\u00c6\u0001\u00a2\u0006\u0002\u0010\u001eJ\u0013\u0010\u001f\u001a\u00020\u00032\b\u0010 \u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010!\u001a\u00020\u000bH\u00d6\u0001J\t\u0010\"\u001a\u00020\u0005H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0019\u0010\b\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0015\u0010\n\u001a\u0004\u0018\u00010\u000b\u00a2\u0006\n\n\u0002\u0010\u0017\u001a\u0004\b\u0015\u0010\u0016\u00a8\u0006#"}, d2 = {"Lcom/akshar/messaging/data/models/ApiResponse;", "", "success", "", "message", "", "status", "Lcom/akshar/messaging/data/models/StatusResponse;", "statuses", "", "viewCount", "", "(ZLjava/lang/String;Lcom/akshar/messaging/data/models/StatusResponse;Ljava/util/List;Ljava/lang/Integer;)V", "getMessage", "()Ljava/lang/String;", "getStatus", "()Lcom/akshar/messaging/data/models/StatusResponse;", "getStatuses", "()Ljava/util/List;", "getSuccess", "()Z", "getViewCount", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "component1", "component2", "component3", "component4", "component5", "copy", "(ZLjava/lang/String;Lcom/akshar/messaging/data/models/StatusResponse;Ljava/util/List;Ljava/lang/Integer;)Lcom/akshar/messaging/data/models/ApiResponse;", "equals", "other", "hashCode", "toString", "app_debug"})
public final class ApiResponse {
    private final boolean success = false;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String message = null;
    @org.jetbrains.annotations.Nullable
    private final com.akshar.messaging.data.models.StatusResponse status = null;
    @org.jetbrains.annotations.Nullable
    private final java.util.List<com.akshar.messaging.data.models.StatusResponse> statuses = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.Integer viewCount = null;
    
    public ApiResponse(boolean success, @org.jetbrains.annotations.NotNull
    java.lang.String message, @org.jetbrains.annotations.Nullable
    com.akshar.messaging.data.models.StatusResponse status, @org.jetbrains.annotations.Nullable
    java.util.List<com.akshar.messaging.data.models.StatusResponse> statuses, @org.jetbrains.annotations.Nullable
    java.lang.Integer viewCount) {
        super();
    }
    
    public final boolean getSuccess() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getMessage() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.akshar.messaging.data.models.StatusResponse getStatus() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.List<com.akshar.messaging.data.models.StatusResponse> getStatuses() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer getViewCount() {
        return null;
    }
    
    public final boolean component1() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.akshar.messaging.data.models.StatusResponse component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.List<com.akshar.messaging.data.models.StatusResponse> component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.akshar.messaging.data.models.ApiResponse copy(boolean success, @org.jetbrains.annotations.NotNull
    java.lang.String message, @org.jetbrains.annotations.Nullable
    com.akshar.messaging.data.models.StatusResponse status, @org.jetbrains.annotations.Nullable
    java.util.List<com.akshar.messaging.data.models.StatusResponse> statuses, @org.jetbrains.annotations.Nullable
    java.lang.Integer viewCount) {
        return null;
    }
    
    @java.lang.Override
    public boolean equals(@org.jetbrains.annotations.Nullable
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public java.lang.String toString() {
        return null;
    }
}