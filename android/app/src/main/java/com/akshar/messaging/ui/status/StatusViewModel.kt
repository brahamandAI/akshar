package com.akshar.messaging.ui.status

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akshar.messaging.data.models.*
import com.akshar.messaging.data.repository.StatusRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File

class StatusViewModel(
    private val statusRepository: StatusRepository = StatusRepository(
        com.akshar.messaging.data.api.RetrofitClient.statusApiService
    )
) : ViewModel() {
    
    private val _statuses = MutableStateFlow<List<StatusResponse>>(emptyList())
    val statuses: StateFlow<List<StatusResponse>> = _statuses.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()
    
    private val _statusStats = MutableStateFlow<StatusStats?>(null)
    val statusStats: StateFlow<StatusStats?> = _statusStats.asStateFlow()
    
    fun loadStatuses(token: String, page: Int = 1, limit: Int = 20) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            statusRepository.getStatuses(token, page, limit).collect { result ->
                _isLoading.value = false
                result.fold(
                    onSuccess = { statusList ->
                        _statuses.value = statusList
                    },
                    onFailure = { exception ->
                        _error.value = exception.message ?: "Failed to load statuses"
                    }
                )
            }
        }
    }
    
    fun loadUserStatuses(token: String, userId: String, page: Int = 1, limit: Int = 20) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            statusRepository.getUserStatuses(token, userId, page, limit).collect { result ->
                _isLoading.value = false
                result.fold(
                    onSuccess = { statusList ->
                        _statuses.value = statusList
                    },
                    onFailure = { exception ->
                        _error.value = exception.message ?: "Failed to load user statuses"
                    }
                )
            }
        }
    }
    
    fun createTextStatus(
        token: String,
        content: String,
        backgroundColor: String,
        fontFamily: String
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            statusRepository.createStatus(
                token = token,
                type = "TEXT",
                content = content,
                backgroundColor = backgroundColor,
                fontFamily = fontFamily
            ).collect { result ->
                _isLoading.value = false
                result.fold(
                    onSuccess = { status ->
                        _statuses.value = listOf(status) + _statuses.value
                    },
                    onFailure = { exception ->
                        _error.value = exception.message ?: "Failed to create text status"
                    }
                )
            }
        }
    }
    
    fun createMusicStatus(
        token: String,
        content: String,
        songTitle: String,
        artist: String,
        duration: String
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            statusRepository.createStatus(
                token = token,
                type = "MUSIC",
                content = content,
                songTitle = songTitle,
                artist = artist,
                duration = duration
            ).collect { result ->
                _isLoading.value = false
                result.fold(
                    onSuccess = { status ->
                        _statuses.value = listOf(status) + _statuses.value
                    },
                    onFailure = { exception ->
                        _error.value = exception.message ?: "Failed to create music status"
                    }
                )
            }
        }
    }
    
    fun createLayoutStatus(
        token: String,
        content: String,
        template: LayoutTemplate
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            statusRepository.createStatus(
                token = token,
                type = "LAYOUT",
                content = content,
                template = template
            ).collect { result ->
                _isLoading.value = false
                result.fold(
                    onSuccess = { status ->
                        _statuses.value = listOf(status) + _statuses.value
                    },
                    onFailure = { exception ->
                        _error.value = exception.message ?: "Failed to create layout status"
                    }
                )
            }
        }
    }
    
    fun createVoiceStatus(
        token: String,
        content: String,
        audioPath: String,
        audioDuration: Long
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            statusRepository.createStatus(
                token = token,
                type = "VOICE",
                content = content,
                audioPath = audioPath,
                audioDuration = audioDuration
            ).collect { result ->
                _isLoading.value = false
                result.fold(
                    onSuccess = { status ->
                        _statuses.value = listOf(status) + _statuses.value
                    },
                    onFailure = { exception ->
                        _error.value = exception.message ?: "Failed to create voice status"
                    }
                )
            }
        }
    }
    
    fun createImageStatus(
        token: String,
        content: String,
        imagePath: String
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            statusRepository.createStatus(
                token = token,
                type = "IMAGE",
                content = content,
                imagePath = imagePath
            ).collect { result ->
                _isLoading.value = false
                result.fold(
                    onSuccess = { status ->
                        _statuses.value = listOf(status) + _statuses.value
                    },
                    onFailure = { exception ->
                        _error.value = exception.message ?: "Failed to create image status"
                    }
                )
            }
        }
    }
    
    fun uploadAudio(token: String, audioFile: File) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            statusRepository.uploadAudio(token, audioFile).collect { result ->
                _isLoading.value = false
                result.fold(
                    onSuccess = { audioPath ->
                        // Audio uploaded successfully
                    },
                    onFailure = { exception ->
                        _error.value = exception.message ?: "Failed to upload audio"
                    }
                )
            }
        }
    }
    
    fun uploadImage(token: String, imageFile: File) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            statusRepository.uploadImage(token, imageFile).collect { result ->
                _isLoading.value = false
                result.fold(
                    onSuccess = { imagePath ->
                        // Image uploaded successfully
                    },
                    onFailure = { exception ->
                        _error.value = exception.message ?: "Failed to upload image"
                    }
                )
            }
        }
    }
    
    fun markStatusAsViewed(token: String, statusId: String) {
        viewModelScope.launch {
            statusRepository.markStatusAsViewed(token, statusId).collect { result ->
                result.fold(
                    onSuccess = { viewCount ->
                        _statuses.value = _statuses.value.map { status ->
                            if (status.id == statusId) {
                                status.copy(
                                    hasUserViewed = true,
                                    viewCount = viewCount
                                )
                            } else {
                                status
                            }
                        }
                    },
                    onFailure = { exception ->
                        _error.value = exception.message ?: "Failed to mark status as viewed"
                    }
                )
            }
        }
    }
    
    fun deleteStatus(token: String, statusId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            statusRepository.deleteStatus(token, statusId).collect { result ->
                _isLoading.value = false
                result.fold(
                    onSuccess = {
                        _statuses.value = _statuses.value.filter { it.id != statusId }
                    },
                    onFailure = { exception ->
                        _error.value = exception.message ?: "Failed to delete status"
                    }
                )
            }
        }
    }
    
    fun loadStatusStats(token: String) {
        viewModelScope.launch {
            statusRepository.getStatusStats(token).collect { result ->
                result.fold(
                    onSuccess = { stats ->
                        _statusStats.value = stats
                    },
                    onFailure = { exception ->
                        _error.value = exception.message ?: "Failed to load status stats"
                    }
                )
            }
        }
    }
    
    fun clearError() {
        _error.value = null
    }
}
