package com.akshar.messaging.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.akshar.messaging.data.api.RetrofitClient
import com.akshar.messaging.data.models.Chat
import com.akshar.messaging.data.models.CreateChatRequest
import com.akshar.messaging.data.models.User
import com.akshar.messaging.data.socket.SocketManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    
    private val apiService = RetrofitClient.apiService
    private val socketManager = SocketManager
    
    private val _chats = MutableStateFlow<List<Chat>>(emptyList())
    val chats: StateFlow<List<Chat>> = _chats.asStateFlow()
    
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()
    
    private val _searchResults = MutableStateFlow<List<User>>(emptyList())
    val searchResults: StateFlow<List<User>> = _searchResults.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()
    
    private var isInitialized = false
    
    init {
        if (!isInitialized) {
            loadUserProfile()
            loadChats()
            // ❌ REMOVED: Don't connect socket here!
            // Socket is already connected in AuthViewModel after login
            setupSocketListeners()
            isInitialized = true
        }
    }
    
    // Removed connectSocket() - already connected in AuthViewModel
    
    private fun setupSocketListeners() {
        viewModelScope.launch {
            socketManager.messageReceived.collect { message ->
                // Reload chats when new message arrives
                loadChats()
            }
        }
    }
    
    fun loadUserProfile() {
        viewModelScope.launch {
            try {
                val response = apiService.getProfile()
                if (response.isSuccessful && response.body() != null) {
                    val responseBody = response.body()!!
                    // Backend returns: { success: true, data: { user: ... } }
                    _currentUser.value = responseBody.data?.user ?: responseBody.user
                    Log.d("HomeViewModel", "Profile loaded: ${_currentUser.value?.firstName}")
                } else {
                    Log.e("HomeViewModel", "Profile load failed: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Profile load error: ${e.message}")
            }
        }
    }
    
    fun updateProfile(firstName: String, lastName: String, bio: String, onComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val request = com.akshar.messaging.data.models.UpdateProfileRequest(
                    firstName = firstName,
                    lastName = lastName,
                    bio = bio
                )
                
                val response = apiService.updateProfile(request)
                if (response.isSuccessful && response.body() != null) {
                    val responseBody = response.body()!!
                    // Backend returns: { success: true, data: { user: ... } }
                    _currentUser.value = responseBody.data?.user ?: responseBody.user
                    Log.d("HomeViewModel", "Profile updated successfully: ${_currentUser.value?.firstName}")
                    onComplete(true)
                } else {
                    Log.e("HomeViewModel", "Profile update failed: ${response.code()}")
                    _errorMessage.value = "Failed to update profile"
                    onComplete(false)
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Profile update error: ${e.message}")
                _errorMessage.value = e.message ?: "Network error"
                onComplete(false)
            }
            _isLoading.value = false
        }
    }
    
    fun uploadProfilePhoto(imageUri: android.net.Uri, context: android.content.Context, onComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Get file from URI
                val contentResolver = context.contentResolver
                val inputStream = contentResolver.openInputStream(imageUri)
                val bytes = inputStream?.readBytes()
                inputStream?.close()
                
                if (bytes == null) {
                    _errorMessage.value = "Failed to read image"
                    onComplete(false)
                    return@launch
                }
                
                // Create multipart body
                val requestFile = okhttp3.RequestBody.create(
                    "image/*".toMediaTypeOrNull(),
                    bytes
                )
                val body = okhttp3.MultipartBody.Part.createFormData("avatar", "avatar.jpg", requestFile)
                
                // Upload to backend
                val response = apiService.uploadAvatar(body)
                if (response.isSuccessful && response.body() != null) {
                    val responseBody = response.body()!!
                    _currentUser.value = responseBody.data?.user ?: responseBody.user
                    Log.d("HomeViewModel", "Avatar uploaded successfully: ${_currentUser.value?.avatar}")
                    onComplete(true)
                } else {
                    _errorMessage.value = "Failed to upload avatar"
                    Log.e("HomeViewModel", "Avatar upload failed: ${response.code()}")
                    onComplete(false)
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Network error"
                Log.e("HomeViewModel", "Avatar upload error: ${e.message}")
                onComplete(false)
            }
            _isLoading.value = false
        }
    }
    
    private var lastChatsLoadTime = 0L
    
    fun loadChats() {
        // Prevent rapid successive calls (debounce - 2 seconds)
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastChatsLoadTime < 2000) {
            Log.d("HomeViewModel", "Skipping loadChats - called too soon")
            return
        }
        lastChatsLoadTime = currentTime
        
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            
            try {
                val response = apiService.getUserChats()
                if (response.isSuccessful && response.body() != null) {
                    _chats.value = response.body()!!.chats
                } else {
                    _errorMessage.value = "Failed to load chats"
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Network error"
                Log.e("HomeViewModel", "Chats load error: ${e.message}")
            }
            
            _isLoading.value = false
        }
    }
    
    fun searchUsers(query: String) {
        if (query.isBlank()) {
            _searchResults.value = emptyList()
            return
        }
        
        viewModelScope.launch {
            try {
                val response = apiService.searchUsers(query)
                if (response.isSuccessful && response.body() != null) {
                    val responseBody = response.body()!!
                    // Backend returns: { success: true, data: { users: [...] } }
                    _searchResults.value = responseBody.data?.users ?: emptyList()
                    Log.d("HomeViewModel", "Search results: ${_searchResults.value.size} users found")
                } else {
                    _searchResults.value = emptyList()
                    Log.d("HomeViewModel", "Search failed: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Search error: ${e.message}")
                _searchResults.value = emptyList()
            }
        }
    }
    
    fun createChat(participantId: String, onSuccess: (String) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            
            try {
                val request = CreateChatRequest(
                    participantId = participantId,
                    isGroup = false,
                    name = null
                )
                
                val response = apiService.createChat(request)
                if (response.isSuccessful && response.body() != null) {
                    val responseBody = response.body()!!
                    // Backend returns: { success: true, data: { chat: {...} } }
                    val chat = responseBody.data?.chat ?: responseBody.chat
                    
                    if (chat != null) {
                        val chatId = chat._id
                        Log.d("HomeViewModel", "Chat created successfully: $chatId")
                        loadChats() // Reload chat list
                        onSuccess(chatId)
                    } else {
                        _errorMessage.value = "Failed to parse chat response"
                        Log.e("HomeViewModel", "Chat data is null in response")
                    }
                } else {
                    _errorMessage.value = "Failed to create chat"
                    Log.e("HomeViewModel", "Create chat failed: ${response.code()}")
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Network error"
                Log.e("HomeViewModel", "Create chat error: ${e.message}")
            }
            
            _isLoading.value = false
        }
    }
    
    fun clearSearch() {
        _searchResults.value = emptyList()
    }
    
    fun archiveChat(chatId: String, onComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val response = apiService.archiveChat(chatId)
                if (response.isSuccessful) {
                    loadChats() // Reload to remove archived chat
                    onComplete(true)
                } else {
                    _errorMessage.value = "Failed to archive chat"
                    onComplete(false)
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Network error"
                Log.e("HomeViewModel", "Archive chat error: ${e.message}")
                onComplete(false)
            }
        }
    }
    
    fun unarchiveChat(chatId: String, onComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val response = apiService.unarchiveChat(chatId)
                if (response.isSuccessful) {
                    loadChats() // Reload to show unarchived chat
                    onComplete(true)
                } else {
                    _errorMessage.value = "Failed to unarchive chat"
                    onComplete(false)
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Network error"
                Log.e("HomeViewModel", "Unarchive chat error: ${e.message}")
                onComplete(false)
            }
        }
    }
    
    fun loadArchivedChats(onComplete: (List<Chat>) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = apiService.getUserChats(includeArchived = true)
                if (response.isSuccessful && response.body() != null) {
                    val responseBody = response.body()!!
                    val chats = responseBody.chats ?: emptyList()
                    val archivedChats = chats.filter { it.isArchived }
                    onComplete(archivedChats)
                } else {
                    onComplete(emptyList())
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Load archived chats error: ${e.message}")
                onComplete(emptyList())
            }
            _isLoading.value = false
        }
    }
    
    fun createGroup(groupName: String, memberIds: List<String>, onComplete: (Boolean) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            
            try {
                val request = com.akshar.messaging.data.models.CreateGroupRequest(
                    groupName = groupName,
                    participants = memberIds,
                    description = null
                )
                
                val response = apiService.createGroup(request)
                if (response.isSuccessful && response.body() != null) {
                    loadChats() // Reload chat list
                    onComplete(true)
                } else {
                    _errorMessage.value = "Failed to create group"
                    onComplete(false)
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Network error"
                Log.e("HomeViewModel", "Create group error: ${e.message}")
                onComplete(false)
            }
            
            _isLoading.value = false
        }
    }
    
    override fun onCleared() {
        super.onCleared()
        // ❌ DON'T disconnect socket - keep it alive throughout app lifecycle
        // socketManager.disconnect()
        Log.d("HomeViewModel", "HomeViewModel cleared (socket remains connected)")
    }
}

