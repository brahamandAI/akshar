package com.akshar.messaging.utils;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0001\u0011B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0002J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rJ\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0005\u001a\u00020\u0006J\u0010\u0010\u0010\u001a\u00020\r2\u0006\u0010\b\u001a\u00020\tH\u0002\u00a8\u0006\u0012"}, d2 = {"Lcom/akshar/messaging/utils/StorageUtil;", "", "()V", "clearCache", "", "context", "Landroid/content/Context;", "deleteDir", "dir", "Ljava/io/File;", "formatSize", "", "bytes", "", "getAppStorageUsage", "Lcom/akshar/messaging/utils/StorageUtil$StorageInfo;", "getDirSize", "StorageInfo", "app_debug"})
public final class StorageUtil {
    @org.jetbrains.annotations.NotNull
    public static final com.akshar.messaging.utils.StorageUtil INSTANCE = null;
    
    private StorageUtil() {
        super();
    }
    
    /**
     * Calculate app storage usage
     */
    @org.jetbrains.annotations.NotNull
    public final com.akshar.messaging.utils.StorageUtil.StorageInfo getAppStorageUsage(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        return null;
    }
    
    /**
     * Calculate directory size recursively
     */
    private final long getDirSize(java.io.File dir) {
        return 0L;
    }
    
    /**
     * Format bytes to human readable format
     */
    @org.jetbrains.annotations.NotNull
    public final java.lang.String formatSize(long bytes) {
        return null;
    }
    
    /**
     * Clear app cache
     */
    public final boolean clearCache(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        return false;
    }
    
    /**
     * Delete directory recursively
     */
    private final boolean deleteDir(java.io.File dir) {
        return false;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0012\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0010\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0011\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0012\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0013\u001a\u00020\u0003H\u00c6\u0003J;\u0010\u0014\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0018\u001a\u00020\u0019H\u00d6\u0001J\t\u0010\u001a\u001a\u00020\u001bH\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\n\u00a8\u0006\u001c"}, d2 = {"Lcom/akshar/messaging/utils/StorageUtil$StorageInfo;", "", "appSize", "", "mediaSize", "cacheSize", "totalSize", "availableSize", "(JJJJJ)V", "getAppSize", "()J", "getAvailableSize", "getCacheSize", "getMediaSize", "getTotalSize", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "", "toString", "", "app_debug"})
    public static final class StorageInfo {
        private final long appSize = 0L;
        private final long mediaSize = 0L;
        private final long cacheSize = 0L;
        private final long totalSize = 0L;
        private final long availableSize = 0L;
        
        public StorageInfo(long appSize, long mediaSize, long cacheSize, long totalSize, long availableSize) {
            super();
        }
        
        public final long getAppSize() {
            return 0L;
        }
        
        public final long getMediaSize() {
            return 0L;
        }
        
        public final long getCacheSize() {
            return 0L;
        }
        
        public final long getTotalSize() {
            return 0L;
        }
        
        public final long getAvailableSize() {
            return 0L;
        }
        
        public final long component1() {
            return 0L;
        }
        
        public final long component2() {
            return 0L;
        }
        
        public final long component3() {
            return 0L;
        }
        
        public final long component4() {
            return 0L;
        }
        
        public final long component5() {
            return 0L;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.akshar.messaging.utils.StorageUtil.StorageInfo copy(long appSize, long mediaSize, long cacheSize, long totalSize, long availableSize) {
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
}