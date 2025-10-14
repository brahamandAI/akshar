package com.akshar.messaging.utils;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\b\u0018\u0000 \u00192\u00020\u0001:\u0001\u0019B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001e\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\f0\u0012H\u0002J\"\u0010\u0013\u001a\u00020\u00072\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006J\"\u0010\u0016\u001a\u00020\u00072\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006J\"\u0010\u0017\u001a\u00020\u00072\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006J\"\u0010\u0018\u001a\u00020\u00072\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010\b\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001a"}, d2 = {"Lcom/akshar/messaging/utils/PermissionsManager;", "", "activity", "Landroidx/activity/ComponentActivity;", "(Landroidx/activity/ComponentActivity;)V", "onPermissionsDenied", "Lkotlin/Function0;", "", "onPermissionsGranted", "permissionLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "", "", "hasPermissions", "", "context", "Landroid/content/Context;", "permissions", "", "requestAudioPermissions", "onGranted", "onDenied", "requestCallPermissions", "requestCameraPermissions", "requestStoragePermissions", "Companion", "app_debug"})
public final class PermissionsManager {
    @org.jetbrains.annotations.NotNull
    private final androidx.activity.ComponentActivity activity = null;
    @org.jetbrains.annotations.Nullable
    private kotlin.jvm.functions.Function0<kotlin.Unit> onPermissionsGranted;
    @org.jetbrains.annotations.Nullable
    private kotlin.jvm.functions.Function0<kotlin.Unit> onPermissionsDenied;
    @org.jetbrains.annotations.NotNull
    private final androidx.activity.result.ActivityResultLauncher<java.lang.String[]> permissionLauncher = null;
    @org.jetbrains.annotations.NotNull
    public static final com.akshar.messaging.utils.PermissionsManager.Companion Companion = null;
    
    public PermissionsManager(@org.jetbrains.annotations.NotNull
    androidx.activity.ComponentActivity activity) {
        super();
    }
    
    public final void requestCameraPermissions(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onGranted, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onDenied) {
    }
    
    public final void requestAudioPermissions(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onGranted, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onDenied) {
    }
    
    public final void requestCallPermissions(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onGranted, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onDenied) {
    }
    
    public final void requestStoragePermissions(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onGranted, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onDenied) {
    }
    
    private final boolean hasPermissions(android.content.Context context, java.util.List<java.lang.String> permissions) {
        return false;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u00a8\u0006\t"}, d2 = {"Lcom/akshar/messaging/utils/PermissionsManager$Companion;", "", "()V", "hasAudioPermission", "", "context", "Landroid/content/Context;", "hasCameraPermission", "hasStoragePermission", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        public final boolean hasCameraPermission(@org.jetbrains.annotations.NotNull
        android.content.Context context) {
            return false;
        }
        
        public final boolean hasAudioPermission(@org.jetbrains.annotations.NotNull
        android.content.Context context) {
            return false;
        }
        
        public final boolean hasStoragePermission(@org.jetbrains.annotations.NotNull
        android.content.Context context) {
            return false;
        }
    }
}