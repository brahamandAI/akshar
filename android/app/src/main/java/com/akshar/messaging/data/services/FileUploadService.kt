package com.akshar.messaging.data.services

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import com.akshar.messaging.data.api.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class FileUploadService(private val context: Context) {
    
    private val apiService = RetrofitClient.apiService
    
    suspend fun uploadImage(uri: Uri, chatId: String): Result<String> = withContext(Dispatchers.IO) {
        try {
            // Compress image
            val compressedFile = compressImage(uri)
            
            // Create multipart
            val requestFile = compressedFile.asRequestBody("image/*".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("file", compressedFile.name, requestFile)
            
            // Upload
            val response = apiService.uploadFile(body, chatId)
            
            if (response.isSuccessful && response.body()?.success == true) {
                val url = response.body()?.data?.url
                compressedFile.delete() // Clean up
                Result.success(url ?: "")
            } else {
                compressedFile.delete()
                Result.failure(Exception("Upload failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun uploadVideo(uri: Uri, chatId: String): Result<String> = withContext(Dispatchers.IO) {
        try {
            // Get video file
            val videoFile = getFileFromUri(uri)
            
            // Create multipart
            val requestFile = videoFile.asRequestBody("video/*".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("file", videoFile.name, requestFile)
            
            // Upload
            val response = apiService.uploadFile(body, chatId)
            
            if (response.isSuccessful && response.body()?.success == true) {
                val url = response.body()?.data?.url
                videoFile.delete() // Clean up
                Result.success(url ?: "")
            } else {
                videoFile.delete()
                Result.failure(Exception("Upload failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    private fun compressImage(uri: Uri): File {
        val bitmap = if (uri.scheme == "content") {
            MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        } else {
            BitmapFactory.decodeFile(uri.path)
        }
        
        // Compress bitmap
        val outputStream = ByteArrayOutputStream()
        var quality = 90
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
        
        // Reduce quality if file is too large
        while (outputStream.toByteArray().size > 1024 * 1024 && quality > 10) { // 1MB
            outputStream.reset()
            quality -= 10
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
        }
        
        // Save to file
        val file = File(context.cacheDir, "compressed_${System.currentTimeMillis()}.jpg")
        FileOutputStream(file).use {
            it.write(outputStream.toByteArray())
        }
        
        bitmap.recycle()
        return file
    }
    
    private fun getFileFromUri(uri: Uri): File {
        val inputStream = context.contentResolver.openInputStream(uri)
        val file = File(context.cacheDir, "temp_${System.currentTimeMillis()}")
        
        inputStream?.use { input ->
            FileOutputStream(file).use { output ->
                input.copyTo(output)
            }
        }
        
        return file
    }
    
    fun getMimeType(uri: Uri): String? {
        return context.contentResolver.getType(uri)
    }
}

