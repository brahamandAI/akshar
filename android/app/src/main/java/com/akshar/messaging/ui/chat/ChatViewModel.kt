package com.akshar.messaging.ui.chat

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.akshar.messaging.data.models.Chat
import com.akshar.messaging.data.models.Message
import com.akshar.messaging.data.repository.ChatRepository
import com.akshar.messaging.data.services.FileUploadService
import com.akshar.messaging.data.socket.ConnectionStatus
import com.akshar.messaging.data.socket.TypingEvent
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ChatViewModel(application: Application) : AndroidViewModel(application) {
    
    private val fileUploadService = FileUploadService(application.applicationContext)
    
    private val chatRepository = ChatRepository()
    
    // Chats list
    private val _chats = MutableStateFlow<List<Chat>>(emptyList())
    val chats: StateFlow<List<Chat>> = _chats.asStateFlow()
    
    // Current chat messages
    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages.asStateFlow()
    
    // Loading state
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    // Error message
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()
    
    // Typing status
    private val _typingUsers = MutableStateFlow<List<String>>(emptyList())
    val typingUsers: StateFlow<List<String>> = _typingUsers.asStateFlow()
    
    // Connection status
    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> = _isConnected.asStateFlow()
    
    // Current chat ID
    private var currentChatId: String? = null
    
    init {
        connectSocket()
        observeMessages()
        observeTyping()
        observeConnection()
    }
    
    private fun connectSocket() {
        chatRepository.connectSocket()
    }
    
    private fun observeMessages() {
        viewModelScope.launch {
            chatRepository.observeMessages().collect { message ->
                // Add new message to current chat
                if (message.chat == currentChatId) {
                    _messages.value = _messages.value + message
                }
                
                // Update last message in chat list
                _chats.value = _chats.value.map { chat ->
                    if (chat._id == message.chat) {
                        chat.copy(lastMessage = message)
                    } else {
                        chat
                    }
                }
            }
        }
    }
    
    private fun observeTyping() {
        viewModelScope.launch {
            chatRepository.observeTyping().collect { event ->
                if (event.chatId == currentChatId) {
                    _typingUsers.value = if (event.isTyping) {
                        _typingUsers.value + event.username
                    } else {
                        _typingUsers.value - event.username
                    }
                }
            }
        }
    }
    
    private fun observeConnection() {
        viewModelScope.launch {
            chatRepository.observeConnectionStatus().collect { status ->
                _isConnected.value = status is ConnectionStatus.Connected
            }
        }
    }
    
    fun loadChats() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            
            val result = chatRepository.getUserChats()
            
            if (result.isSuccess) {
                _chats.value = result.getOrNull() ?: emptyList()
            } else {
                _errorMessage.value = result.exceptionOrNull()?.message ?: "Failed to load chats"
            }
            
            _isLoading.value = false
        }
    }
    
    fun loadChat(chatId: String) {
        currentChatId = chatId
        chatRepository.joinChat(chatId)
        loadMessages(chatId)
    }
    
    fun openChat(chatId: String) {
        currentChatId = chatId
        chatRepository.joinChat(chatId)
        loadMessages(chatId)
    }
    
    fun closeChat() {
        currentChatId?.let { chatRepository.leaveChat(it) }
        currentChatId = null
        _messages.value = emptyList()
    }
    
    private fun loadMessages(chatId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            
            val result = chatRepository.getChatMessages(chatId)
            
            if (result.isSuccess) {
                _messages.value = result.getOrNull() ?: emptyList()
            } else {
                _errorMessage.value = result.exceptionOrNull()?.message ?: "Failed to load messages"
            }
            
            _isLoading.value = false
        }
    }
    
    fun sendMessage(content: String) {
        val chatId = currentChatId ?: return
        
        viewModelScope.launch {
            val result = chatRepository.sendMessage(chatId, content)
            
            if (result.isFailure) {
                _errorMessage.value = result.exceptionOrNull()?.message ?: "Failed to send message"
            }
        }
    }
    
    fun startTyping() {
        currentChatId?.let { chatRepository.sendTyping(it) }
    }
    
    fun stopTyping() {
        currentChatId?.let { chatRepository.stopTyping(it) }
    }
    
    fun createChat(participantId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            
            val result = chatRepository.createChat(participantId)
            
            if (result.isSuccess) {
                val chat = result.getOrNull()
                chat?.let {
                    _chats.value = listOf(it) + _chats.value
                    openChat(it._id)
                }
            } else {
                _errorMessage.value = result.exceptionOrNull()?.message ?: "Failed to create chat"
            }
            
            _isLoading.value = false
        }
    }
    
    fun clearError() {
        _errorMessage.value = null
    }
    
    // Media upload methods
    fun sendImageMessage(uri: Uri) {
        val chatId = currentChatId ?: return
        
        viewModelScope.launch {
            _isLoading.value = true
            
            val result = fileUploadService.uploadImage(uri, chatId)
            
            if (result.isSuccess) {
                val imageUrl = result.getOrNull()
                imageUrl?.let {
                    chatRepository.sendMessage(chatId, imageUrl, "image")
                }
            } else {
                _errorMessage.value = "Failed to upload image"
            }
            
            _isLoading.value = false
        }
    }
    
    fun sendVideoMessage(uri: Uri) {
        val chatId = currentChatId ?: return
        
        viewModelScope.launch {
            _isLoading.value = true
            
            val result = fileUploadService.uploadVideo(uri, chatId)
            
            if (result.isSuccess) {
                val videoUrl = result.getOrNull()
                videoUrl?.let {
                    chatRepository.sendMessage(chatId, videoUrl, "video")
                }
            } else {
                _errorMessage.value = "Failed to upload video"
            }
            
            _isLoading.value = false
        }
    }
    
    override fun onCleared() {
        super.onCleared()
        closeChat()
        chatRepository.disconnectSocket()
    }
}

