package com.akshar.messaging.utils;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\b\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\n\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u000b\u001a\u00020\fJ\u0010\u0010\r\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u000b\u001a\u00020\fJ\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u000b\u001a\u00020\fJ\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u000b\u001a\u00020\fJ\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u000b\u001a\u00020\fJ\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u000b\u001a\u00020\fJ\u000e\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u000b\u001a\u00020\fJ\u000e\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u000b\u001a\u00020\fJ\u0016\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0019\u001a\u00020\u0004J.\u0010\u001a\u001a\u00020\u00172\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u001b\u001a\u00020\u00042\u0006\u0010\u001c\u001a\u00020\u00042\u0006\u0010\u001d\u001a\u00020\u00042\u0006\u0010\u001e\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001f"}, d2 = {"Lcom/akshar/messaging/utils/TokenManager;", "", "()V", "KEY_EMAIL", "", "KEY_FULL_NAME", "KEY_TOKEN", "KEY_USERNAME", "KEY_USER_ID", "PREF_NAME", "getBearerToken", "context", "Landroid/content/Context;", "getEmail", "getFullName", "getPreferences", "Landroid/content/SharedPreferences;", "getToken", "getUserId", "getUsername", "isLoggedIn", "", "logout", "", "saveToken", "token", "saveUserInfo", "userId", "username", "email", "fullName", "app_debug"})
public final class TokenManager {
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String PREF_NAME = "AksharAuth";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String KEY_TOKEN = "auth_token";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String KEY_USER_ID = "user_id";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String KEY_USERNAME = "username";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String KEY_EMAIL = "email";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String KEY_FULL_NAME = "full_name";
    @org.jetbrains.annotations.NotNull
    public static final com.akshar.messaging.utils.TokenManager INSTANCE = null;
    
    private TokenManager() {
        super();
    }
    
    private final android.content.SharedPreferences getPreferences(android.content.Context context) {
        return null;
    }
    
    public final void saveToken(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    java.lang.String token) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getToken(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getBearerToken(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        return null;
    }
    
    public final void saveUserInfo(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    java.lang.String userId, @org.jetbrains.annotations.NotNull
    java.lang.String username, @org.jetbrains.annotations.NotNull
    java.lang.String email, @org.jetbrains.annotations.NotNull
    java.lang.String fullName) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getUserId(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getUsername(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getEmail(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getFullName(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        return null;
    }
    
    public final boolean isLoggedIn(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        return false;
    }
    
    public final void logout(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
    }
}