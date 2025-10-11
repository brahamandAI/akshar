package com.akshar.messaging.data.socket

import android.util.Log
import com.akshar.messaging.data.api.RetrofitClient
import com.akshar.messaging.data.models.Message
import com.google.gson.Gson
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import org.json.JSONObject
import java.net.URI

object SocketManager {
    
    // For physical device - Using your computer's IP
    private const val SERVER_URL = "http://10.234.138.233:3000"
    // For Android Emulator use: "http://10.0.2.2:3000"
    
    @Volatile
    private var socket: Socket? = null
    private val gson = Gson()
    
    // Message events
    private val _messageReceived = MutableSharedFlow<Message>()
    val messageReceived: SharedFlow<Message> = _messageReceived.asSharedFlow()
    
    // Typing events
    private val _typingEvent = MutableSharedFlow<TypingEvent>()
    val typingEvent: SharedFlow<TypingEvent> = _typingEvent.asSharedFlow()
    
    // Connection events
    private val _connectionStatus = MutableSharedFlow<ConnectionStatus>()
    val connectionStatus: SharedFlow<ConnectionStatus> = _connectionStatus.asSharedFlow()
    
    @Synchronized
    fun connect() {
        try {
            // Check token FIRST before any socket operations
            val token = RetrofitClient.getToken()
            if (token.isNullOrEmpty()) {
                Log.e("SocketManager", "❌ Auth token missing. Skipping socket connect.")
                return
            }
            
            // ✅ CRITICAL: If socket already exists and connected, DO NOTHING
            if (socket?.connected() == true) {
                Log.d("SocketManager", "✅ Socket already connected (ID: ${socket?.id()}), skipping...")
                return
            }
            
            // ✅ If socket exists but disconnected, just reconnect (don't recreate)
            if (socket != null && !socket!!.connected()) {
                Log.d("SocketManager", "🔄 Socket exists but disconnected, reconnecting...")
                socket?.connect()
                return
            }
            
            // ✅ Only create NEW socket if none exists
            if (socket == null) {
                Log.d("SocketManager", "🆕 Creating NEW socket connection...")
                Log.d("SocketManager", "🔑 Token: ${token.take(30)}... (length: ${token.length})")
                
                val options = IO.Options().apply {
                    auth = mapOf("token" to token)
                    transports = arrayOf("websocket", "polling")
                    reconnection = true
                    reconnectionAttempts = 10
                    reconnectionDelay = 2000
                    reconnectionDelayMax = 5000
                    timeout = 20000
                    forceNew = false
                }
                
                socket = IO.socket(URI.create(SERVER_URL), options)
                
                socket?.apply {
                    on(Socket.EVENT_CONNECT, onConnect)
                    on(Socket.EVENT_DISCONNECT, onDisconnect)
                    on(Socket.EVENT_CONNECT_ERROR, onConnectError)
                    on("message_received", onMessageReceived)
                    on("typing", onTyping)
                    on("stop_typing", onStopTyping)
                    on("user_online", onUserOnline)
                    on("user_offline", onUserOffline)
                    
                    connect()
                }
                
                Log.d("SocketManager", "🚀 Socket connecting...")
            }
        } catch (e: Exception) {
            Log.e("SocketManager", "❌ Error connecting socket", e)
        }
    }
    
    @Synchronized
    fun disconnect() {
        socket?.apply {
            off()
            disconnect()
        }
        socket = null
        Log.d("SocketManager", "🔌 Socket disconnected and reset")
    }
    
    fun joinChat(chatId: String) {
        socket?.emit("join_chat", JSONObject().apply {
            put("chatId", chatId)
        })
        Log.d("SocketManager", "Joined chat: $chatId")
    }
    
    fun leaveChat(chatId: String) {
        socket?.emit("leave_chat", JSONObject().apply {
            put("chatId", chatId)
        })
        Log.d("SocketManager", "Left chat: $chatId")
    }
    
    fun sendMessage(chatId: String, content: String, contentType: String = "text") {
        socket?.emit("send_message", JSONObject().apply {
            put("chatId", chatId)
            put("content", content)
            put("contentType", contentType)
        })
        Log.d("SocketManager", "Message sent to chat: $chatId")
    }
    
    fun sendTyping(chatId: String) {
        socket?.emit("typing", JSONObject().apply {
            put("chatId", chatId)
        })
    }
    
    fun sendStopTyping(chatId: String) {
        socket?.emit("stop_typing", JSONObject().apply {
            put("chatId", chatId)
        })
    }
    
    fun markMessageAsRead(messageId: String) {
        socket?.emit("message_read", JSONObject().apply {
            put("messageId", messageId)
        })
    }
    
    // Event Listeners
    private val onConnect = Emitter.Listener {
        Log.d("SocketManager", "Socket connected")
        _connectionStatus.tryEmit(ConnectionStatus.Connected)
    }
    
    private val onDisconnect = Emitter.Listener {
        Log.d("SocketManager", "Socket disconnected")
        _connectionStatus.tryEmit(ConnectionStatus.Disconnected)
    }
    
    private val onConnectError = Emitter.Listener { args ->
        Log.e("SocketManager", "Socket connection error: ${args[0]}")
        _connectionStatus.tryEmit(ConnectionStatus.Error(args[0].toString()))
    }
    
    private val onMessageReceived = Emitter.Listener { args ->
        try {
            val data = args[0] as JSONObject
            val message = gson.fromJson(data.toString(), Message::class.java)
            _messageReceived.tryEmit(message)
            Log.d("SocketManager", "Message received: ${message.id}")
        } catch (e: Exception) {
            Log.e("SocketManager", "Error parsing message", e)
        }
    }
    
    private val onTyping = Emitter.Listener { args ->
        try {
            val data = args[0] as JSONObject
            val chatId = data.getString("chatId")
            val userId = data.getString("userId")
            val username = data.optString("username", "Someone")
            _typingEvent.tryEmit(TypingEvent(chatId, userId, username, true))
        } catch (e: Exception) {
            Log.e("SocketManager", "Error parsing typing event", e)
        }
    }
    
    private val onStopTyping = Emitter.Listener { args ->
        try {
            val data = args[0] as JSONObject
            val chatId = data.getString("chatId")
            val userId = data.getString("userId")
            _typingEvent.tryEmit(TypingEvent(chatId, userId, "", false))
        } catch (e: Exception) {
            Log.e("SocketManager", "Error parsing stop typing event", e)
        }
    }
    
    private val onUserOnline = Emitter.Listener { args ->
        try {
            val data = args[0] as JSONObject
            val userId = data.getString("userId")
            Log.d("SocketManager", "User online: $userId")
        } catch (e: Exception) {
            Log.e("SocketManager", "Error parsing user online event", e)
        }
    }
    
    private val onUserOffline = Emitter.Listener { args ->
        try {
            val data = args[0] as JSONObject
            val userId = data.getString("userId")
            Log.d("SocketManager", "User offline: $userId")
        } catch (e: Exception) {
            Log.e("SocketManager", "Error parsing user offline event", e)
        }
    }
    
    fun isConnected(): Boolean = socket?.connected() ?: false
}

// Supporting Classes
data class TypingEvent(
    val chatId: String,
    val userId: String,
    val username: String,
    val isTyping: Boolean
)

sealed class ConnectionStatus {
    object Connected : ConnectionStatus()
    object Disconnected : ConnectionStatus()
    data class Error(val message: String) : ConnectionStatus()
}

