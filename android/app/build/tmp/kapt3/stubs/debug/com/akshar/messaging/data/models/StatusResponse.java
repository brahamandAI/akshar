package com.akshar.messaging.data.models;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b1\b\u0086\b\u0018\u00002\u00020\u0001B\u009f\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\f\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0011\u001a\u00020\u0012\u0012\u0006\u0010\u0013\u001a\u00020\u0014\u0012\u0006\u0010\u0015\u001a\u00020\u0003\u0012\u0006\u0010\u0016\u001a\u00020\u0003\u0012\u0006\u0010\u0017\u001a\u00020\u0018\u00a2\u0006\u0002\u0010\u0019J\t\u00102\u001a\u00020\u0003H\u00c6\u0003J\u000b\u00103\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u0010\u00104\u001a\u0004\u0018\u00010\u000fH\u00c6\u0003\u00a2\u0006\u0002\u0010\u001dJ\u000b\u00105\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u00106\u001a\u00020\u0012H\u00c6\u0003J\t\u00107\u001a\u00020\u0014H\u00c6\u0003J\t\u00108\u001a\u00020\u0003H\u00c6\u0003J\t\u00109\u001a\u00020\u0003H\u00c6\u0003J\t\u0010:\u001a\u00020\u0018H\u00c6\u0003J\t\u0010;\u001a\u00020\u0003H\u00c6\u0003J\t\u0010<\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010=\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010>\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010?\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010@\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010A\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010B\u001a\u0004\u0018\u00010\fH\u00c6\u0003J\u00ca\u0001\u0010C\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0011\u001a\u00020\u00122\b\b\u0002\u0010\u0013\u001a\u00020\u00142\b\b\u0002\u0010\u0015\u001a\u00020\u00032\b\b\u0002\u0010\u0016\u001a\u00020\u00032\b\b\u0002\u0010\u0017\u001a\u00020\u0018H\u00c6\u0001\u00a2\u0006\u0002\u0010DJ\u0013\u0010E\u001a\u00020\u00142\b\u0010F\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010G\u001a\u00020\u0012H\u00d6\u0001J\t\u0010H\u001a\u00020\u0003H\u00d6\u0001R\u0013\u0010\t\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0015\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u00a2\u0006\n\n\u0002\u0010\u001e\u001a\u0004\b\u001c\u0010\u001dR\u0013\u0010\r\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u001bR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u001bR\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001bR\u0011\u0010\u0015\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u001bR\u0013\u0010\n\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u001bR\u0011\u0010\u0016\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010\u001bR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010\u001bR\u0011\u0010\u0013\u001a\u00020\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\'R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010\u001bR\u0013\u0010\u0010\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010\u001bR\u0013\u0010\b\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010\u001bR\u0013\u0010\u000b\u001a\u0004\u0018\u00010\f\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010,R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010\u001bR\u0011\u0010\u0017\u001a\u00020\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b.\u0010/R\u0011\u0010\u0011\u001a\u00020\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b0\u00101\u00a8\u0006I"}, d2 = {"Lcom/akshar/messaging/data/models/StatusResponse;", "", "id", "", "type", "content", "backgroundColor", "fontFamily", "songTitle", "artist", "duration", "template", "Lcom/akshar/messaging/data/models/LayoutTemplate;", "audioPath", "audioDuration", "", "imagePath", "viewCount", "", "hasUserViewed", "", "createdAt", "expiresAt", "user", "Lcom/akshar/messaging/data/models/StatusUser;", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/akshar/messaging/data/models/LayoutTemplate;Ljava/lang/String;Ljava/lang/Long;Ljava/lang/String;IZLjava/lang/String;Ljava/lang/String;Lcom/akshar/messaging/data/models/StatusUser;)V", "getArtist", "()Ljava/lang/String;", "getAudioDuration", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getAudioPath", "getBackgroundColor", "getContent", "getCreatedAt", "getDuration", "getExpiresAt", "getFontFamily", "getHasUserViewed", "()Z", "getId", "getImagePath", "getSongTitle", "getTemplate", "()Lcom/akshar/messaging/data/models/LayoutTemplate;", "getType", "getUser", "()Lcom/akshar/messaging/data/models/StatusUser;", "getViewCount", "()I", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/akshar/messaging/data/models/LayoutTemplate;Ljava/lang/String;Ljava/lang/Long;Ljava/lang/String;IZLjava/lang/String;Ljava/lang/String;Lcom/akshar/messaging/data/models/StatusUser;)Lcom/akshar/messaging/data/models/StatusResponse;", "equals", "other", "hashCode", "toString", "app_debug"})
public final class StatusResponse {
    @org.jetbrains.annotations.NotNull
    private final java.lang.String id = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String type = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String content = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String backgroundColor = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String fontFamily = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String songTitle = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String artist = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String duration = null;
    @org.jetbrains.annotations.Nullable
    private final com.akshar.messaging.data.models.LayoutTemplate template = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String audioPath = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.Long audioDuration = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String imagePath = null;
    private final int viewCount = 0;
    private final boolean hasUserViewed = false;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String createdAt = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String expiresAt = null;
    @org.jetbrains.annotations.NotNull
    private final com.akshar.messaging.data.models.StatusUser user = null;
    
    public StatusResponse(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
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
    java.lang.String imagePath, int viewCount, boolean hasUserViewed, @org.jetbrains.annotations.NotNull
    java.lang.String createdAt, @org.jetbrains.annotations.NotNull
    java.lang.String expiresAt, @org.jetbrains.annotations.NotNull
    com.akshar.messaging.data.models.StatusUser user) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getType() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getContent() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getBackgroundColor() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getFontFamily() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getSongTitle() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getArtist() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getDuration() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.akshar.messaging.data.models.LayoutTemplate getTemplate() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getAudioPath() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Long getAudioDuration() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getImagePath() {
        return null;
    }
    
    public final int getViewCount() {
        return 0;
    }
    
    public final boolean getHasUserViewed() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getCreatedAt() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getExpiresAt() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.akshar.messaging.data.models.StatusUser getUser() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component10() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Long component11() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component12() {
        return null;
    }
    
    public final int component13() {
        return 0;
    }
    
    public final boolean component14() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component15() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component16() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.akshar.messaging.data.models.StatusUser component17() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.akshar.messaging.data.models.LayoutTemplate component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.akshar.messaging.data.models.StatusResponse copy(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
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
    java.lang.String imagePath, int viewCount, boolean hasUserViewed, @org.jetbrains.annotations.NotNull
    java.lang.String createdAt, @org.jetbrains.annotations.NotNull
    java.lang.String expiresAt, @org.jetbrains.annotations.NotNull
    com.akshar.messaging.data.models.StatusUser user) {
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