package com.akshar.messaging.data.models;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u001f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0081\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0010J\t\u0010 \u001a\u00020\u0003H\u00c6\u0003J\u0010\u0010!\u001a\u0004\u0018\u00010\u000eH\u00c6\u0003\u00a2\u0006\u0002\u0010\u0014J\u000b\u0010\"\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010#\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010$\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010%\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010&\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010\'\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010(\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010)\u001a\u0004\u0018\u00010\u000bH\u00c6\u0003J\u000b\u0010*\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u008e\u0001\u0010+\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001\u00a2\u0006\u0002\u0010,J\u0013\u0010-\u001a\u00020.2\b\u0010/\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00100\u001a\u000201H\u00d6\u0001J\t\u00102\u001a\u00020\u0003H\u00d6\u0001R\u0013\u0010\b\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0015\u0010\r\u001a\u0004\u0018\u00010\u000e\u00a2\u0006\n\n\u0002\u0010\u0015\u001a\u0004\b\u0013\u0010\u0014R\u0013\u0010\f\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0012R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0012R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0012R\u0013\u0010\t\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0012R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0012R\u0013\u0010\u000f\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0012R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0012R\u0013\u0010\n\u001a\u0004\u0018\u00010\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0012\u00a8\u00063"}, d2 = {"Lcom/akshar/messaging/data/models/StatusRequest;", "", "type", "", "content", "backgroundColor", "fontFamily", "songTitle", "artist", "duration", "template", "Lcom/akshar/messaging/data/models/LayoutTemplate;", "audioPath", "audioDuration", "", "imagePath", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/akshar/messaging/data/models/LayoutTemplate;Ljava/lang/String;Ljava/lang/Long;Ljava/lang/String;)V", "getArtist", "()Ljava/lang/String;", "getAudioDuration", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getAudioPath", "getBackgroundColor", "getContent", "getDuration", "getFontFamily", "getImagePath", "getSongTitle", "getTemplate", "()Lcom/akshar/messaging/data/models/LayoutTemplate;", "getType", "component1", "component10", "component11", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/akshar/messaging/data/models/LayoutTemplate;Ljava/lang/String;Ljava/lang/Long;Ljava/lang/String;)Lcom/akshar/messaging/data/models/StatusRequest;", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
public final class StatusRequest {
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
    
    public StatusRequest(@org.jetbrains.annotations.NotNull
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
    java.lang.String imagePath) {
        super();
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
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Long component10() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component11() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
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
    public final com.akshar.messaging.data.models.LayoutTemplate component8() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.akshar.messaging.data.models.StatusRequest copy(@org.jetbrains.annotations.NotNull
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
    java.lang.String imagePath) {
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