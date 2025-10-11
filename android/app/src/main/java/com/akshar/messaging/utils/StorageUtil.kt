package com.akshar.messaging.utils

import android.content.Context
import android.os.Environment
import android.os.StatFs
import java.io.File

object StorageUtil {
    
    data class StorageInfo(
        val appSize: Long,
        val mediaSize: Long,
        val cacheSize: Long,
        val totalSize: Long,
        val availableSize: Long
    )
    
    /**
     * Calculate app storage usage
     */
    fun getAppStorageUsage(context: Context): StorageInfo {
        val appDir = context.filesDir
        val cacheDir = context.cacheDir
        val externalFilesDir = context.getExternalFilesDir(null)
        
        val appSize = getDirSize(appDir)
        val cacheSize = getDirSize(cacheDir)
        val mediaSize = if (externalFilesDir != null) getDirSize(externalFilesDir) else 0L
        
        // Get device storage info
        val path = Environment.getDataDirectory()
        val stat = StatFs(path.path)
        val blockSize = stat.blockSizeLong
        val totalBlocks = stat.blockCountLong
        val availableBlocks = stat.availableBlocksLong
        
        return StorageInfo(
            appSize = appSize,
            mediaSize = mediaSize,
            cacheSize = cacheSize,
            totalSize = totalBlocks * blockSize,
            availableSize = availableBlocks * blockSize
        )
    }
    
    /**
     * Calculate directory size recursively
     */
    private fun getDirSize(dir: File): Long {
        var size = 0L
        
        if (dir.exists()) {
            val files = dir.listFiles()
            if (files != null) {
                for (file in files) {
                    size += if (file.isDirectory) {
                        getDirSize(file)
                    } else {
                        file.length()
                    }
                }
            }
        }
        
        return size
    }
    
    /**
     * Format bytes to human readable format
     */
    fun formatSize(bytes: Long): String {
        if (bytes < 1024) return "$bytes B"
        val kb = bytes / 1024.0
        if (kb < 1024) return String.format("%.1f KB", kb)
        val mb = kb / 1024.0
        if (mb < 1024) return String.format("%.1f MB", mb)
        val gb = mb / 1024.0
        return String.format("%.2f GB", gb)
    }
    
    /**
     * Clear app cache
     */
    fun clearCache(context: Context): Boolean {
        return try {
            val cacheDir = context.cacheDir
            deleteDir(cacheDir)
            true
        } catch (e: Exception) {
            false
        }
    }
    
    /**
     * Delete directory recursively
     */
    private fun deleteDir(dir: File): Boolean {
        if (dir.isDirectory) {
            val children = dir.list()
            if (children != null) {
                for (child in children) {
                    val success = deleteDir(File(dir, child))
                    if (!success) {
                        return false
                    }
                }
            }
        }
        return dir.delete()
    }
}

