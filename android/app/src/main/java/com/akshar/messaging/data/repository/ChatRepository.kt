package com.akshar.messaging.data.repository

import com.akshar.messaging.data.api.RetrofitClient
import com.akshar.messaging.data.models.*
import com.akshar.messaging.data.socket.SocketManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class ChatRepository {
    
    private val apiService = RetrofitClient.apiService
    
    // Get user's chats
    suspend fun getUserChats(): Result<List<Chat>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getUserChats()
            
            if (response.isSuccessful && response.body()?.success == true) {
                Result.success(response.body()?.chats ?: emptyList())
            } else {
                Result.failure(Exception(response.message() ?: "Failed to get chats"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // Create new chat
    suspend fun createChat(participantId: String, isGroupChat: Boolean = false): Result<Chat> = withContext(Dispatchers.IO) {
        try {
            val request = CreateChatRequest(participantId, isGroupChat)
            val response = apiService.createChat(request)
            
            if (response.isSuccessful && response.body()?.success == true) {
                val chat = response.body()?.chat
                chat?.let { SocketManager.joinChat(it._id) }
                Result.success(chat!!)
            } else {
                Result.failure(Exception(response.message() ?: "Failed to create chat"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // Get chat messages
    suspend fun getChatMessages(chatId: String): Result<List<Message>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getChatMessages(chatId)
            
            if (response.isSuccessful && response.body()?.success == true) {
                Result.success(response.body()?.messages ?: emptyList())
            } else {
                Result.failure(Exception(response.message() ?: "Failed to get messages"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // Send message
    suspend fun sendMessage(chatId: String, content: String, contentType: String = "text"): Result<Message> = withContext(Dispatchers.IO) {
        try {
            val request = SendMessageRequest(content, contentType)
            val response = apiService.sendMessage(chatId, request)
            
            if (response.isSuccessful && response.body()?.success == true) {
                Result.success(response.body()?.message!!)
            } else {
                Result.failure(Exception(response.message() ?: "Failed to send message"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    // Real-time message updates
    fun observeMessages(): Flow<Message> = SocketManager.messageReceived
    
    // Typing events
    fun observeTyping(): Flow<com.akshar.messaging.data.socket.TypingEvent> = SocketManager.typingEvent
    
    // Connection status
    fun observeConnectionStatus(): Flow<com.akshar.messaging.data.socket.ConnectionStatus> = SocketManager.connectionStatus
    
    // Socket operations
    fun connectSocket() = SocketManager.connect()
    
    fun disconnectSocket() = SocketManager.disconnect()
    
    fun joinChat(chatId: String) = SocketManager.joinChat(chatId)
    
    fun leaveChat(chatId: String) = SocketManager.leaveChat(chatId)
    
    fun sendTyping(chatId: String) = SocketManager.sendTyping(chatId)
    
    fun stopTyping(chatId: String) = SocketManager.sendStopTyping(chatId)
    
    fun markAsRead(messageId: String) = SocketManager.markMessageAsRead(messageId)
}

