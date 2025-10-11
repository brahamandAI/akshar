package com.akshar.messaging.data.repository

import com.akshar.messaging.data.api.StatusApiService
import com.akshar.messaging.data.models.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StatusRepository @Inject constructor(
    private val statusApiService: StatusApiService
) {
    
    suspend fun createStatus(
        token: String,
        type: String,
        content: String,
        backgroundColor: String? = null,
        fontFamily: String? = null,
        songTitle: String? = null,
        artist: String? = null,
        duration: String? = null,
        template: LayoutTemplate? = null,
        audioPath: String? = null,
        audioDuration: Long? = null,
        imagePath: String? = null
    ): Flow<Result<StatusResponse>> = flow {
        try {
            val request = StatusRequest(
                type = type,
                content = content,
                backgroundColor = backgroundColor,
                fontFamily = fontFamily,
                songTitle = songTitle,
                artist = artist,
                duration = duration,
                template = template,
                audioPath = audioPath,
                audioDuration = audioDuration,
                imagePath = imagePath
            )
            
            val response = statusApiService.createStatus(token, request)
            
            if (response.isSuccessful && response.body()?.success == true) {
                val status = response.body()?.status
                if (status != null) {
                    emit(Result.success(status))
                } else {
                    emit(Result.failure(Exception("Status data is null")))
                }
            } else {
                val errorMessage = response.body()?.message ?: "Failed to create status"
                emit(Result.failure(Exception(errorMessage)))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
    
    suspend fun getStatuses(
        token: String,
        page: Int = 1,
        limit: Int = 20
    ): Flow<Result<List<StatusResponse>>> = flow {
        try {
            val response = statusApiService.getStatuses(token, page, limit)
            
            if (response.isSuccessful && response.body()?.success == true) {
                val statuses = response.body()?.statuses ?: emptyList()
                emit(Result.success(statuses))
            } else {
                val errorMessage = response.body()?.message ?: "Failed to fetch statuses"
                emit(Result.failure(Exception(errorMessage)))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
    
    suspend fun getUserStatuses(
        token: String,
        userId: String,
        page: Int = 1,
        limit: Int = 20
    ): Flow<Result<List<StatusResponse>>> = flow {
        try {
            val response = statusApiService.getUserStatuses(token, userId, page, limit)
            
            if (response.isSuccessful && response.body()?.success == true) {
                val statuses = response.body()?.statuses ?: emptyList()
                emit(Result.success(statuses))
            } else {
                val errorMessage = response.body()?.message ?: "Failed to fetch user statuses"
                emit(Result.failure(Exception(errorMessage)))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
    
    suspend fun markStatusAsViewed(
        token: String,
        statusId: String
    ): Flow<Result<Int>> = flow {
        try {
            val response = statusApiService.markStatusAsViewed(token, statusId)
            
            if (response.isSuccessful && response.body()?.success == true) {
                val viewCount = response.body()?.viewCount ?: 0
                emit(Result.success(viewCount))
            } else {
                val errorMessage = response.body()?.message ?: "Failed to mark status as viewed"
                emit(Result.failure(Exception(errorMessage)))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
    
    suspend fun deleteStatus(
        token: String,
        statusId: String
    ): Flow<Result<Unit>> = flow {
        try {
            val response = statusApiService.deleteStatus(token, statusId)
            
            if (response.isSuccessful && response.body()?.success == true) {
                emit(Result.success(Unit))
            } else {
                val errorMessage = response.body()?.message ?: "Failed to delete status"
                emit(Result.failure(Exception(errorMessage)))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
    
    suspend fun getStatusStats(
        token: String
    ): Flow<Result<StatusStats>> = flow {
        try {
            val response = statusApiService.getStatusStats(token)
            
            if (response.isSuccessful && response.body()?.success == true) {
                val stats = response.body()?.stats
                if (stats != null) {
                    emit(Result.success(stats))
                } else {
                    emit(Result.failure(Exception("Stats data is null")))
                }
            } else {
                val errorMessage = response.body()?.message ?: "Failed to fetch status stats"
                emit(Result.failure(Exception(errorMessage)))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
    
    suspend fun uploadAudio(
        token: String,
        audioFile: File
    ): Flow<Result<String>> = flow {
        try {
            val requestFile = audioFile.asRequestBody("audio/*".toMediaTypeOrNull())
            val audioPart = MultipartBody.Part.createFormData("audio", audioFile.name, requestFile)
            
            val response = statusApiService.uploadAudio(token, audioPart)
            
            if (response.isSuccessful && response.body()?.success == true) {
                val audioPath = response.body()?.audioPath
                if (audioPath != null) {
                    emit(Result.success(audioPath))
                } else {
                    emit(Result.failure(Exception("Audio path is null")))
                }
            } else {
                val errorMessage = response.body()?.message ?: "Failed to upload audio"
                emit(Result.failure(Exception(errorMessage)))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
    
    suspend fun uploadImage(
        token: String,
        imageFile: File
    ): Flow<Result<String>> = flow {
        try {
            val requestFile = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
            val imagePart = MultipartBody.Part.createFormData("image", imageFile.name, requestFile)
            
            val response = statusApiService.uploadImage(token, imagePart)
            
            if (response.isSuccessful && response.body()?.success == true) {
                val imagePath = response.body()?.imagePath
                if (imagePath != null) {
                    emit(Result.success(imagePath))
                } else {
                    emit(Result.failure(Exception("Image path is null")))
                }
            } else {
                val errorMessage = response.body()?.message ?: "Failed to upload image"
                emit(Result.failure(Exception(errorMessage)))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}
