package com.akshar.messaging.data.socket;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u000b\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010!\u001a\u00020\"J\u0006\u0010#\u001a\u00020\"J\u0006\u0010$\u001a\u00020%J\u000e\u0010&\u001a\u00020\"2\u0006\u0010\'\u001a\u00020\u0004J\u000e\u0010(\u001a\u00020\"2\u0006\u0010\'\u001a\u00020\u0004J\u000e\u0010)\u001a\u00020\"2\u0006\u0010*\u001a\u00020\u0004J \u0010+\u001a\u00020\"2\u0006\u0010\'\u001a\u00020\u00042\u0006\u0010,\u001a\u00020\u00042\b\b\u0002\u0010-\u001a\u00020\u0004J\u000e\u0010.\u001a\u00020\"2\u0006\u0010\'\u001a\u00020\u0004J\u000e\u0010/\u001a\u00020\"2\u0006\u0010\'\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00070\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\t0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000fR\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u001eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u000b0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u000f\u00a8\u00060"}, d2 = {"Lcom/akshar/messaging/data/socket/SocketManager;", "", "()V", "SERVER_URL", "", "_connectionStatus", "Lkotlinx/coroutines/flow/MutableSharedFlow;", "Lcom/akshar/messaging/data/socket/ConnectionStatus;", "_messageReceived", "Lcom/akshar/messaging/data/models/Message;", "_typingEvent", "Lcom/akshar/messaging/data/socket/TypingEvent;", "connectionStatus", "Lkotlinx/coroutines/flow/SharedFlow;", "getConnectionStatus", "()Lkotlinx/coroutines/flow/SharedFlow;", "gson", "Lcom/google/gson/Gson;", "messageReceived", "getMessageReceived", "onConnect", "Lio/socket/emitter/Emitter$Listener;", "onConnectError", "onDisconnect", "onMessageReceived", "onStopTyping", "onTyping", "onUserOffline", "onUserOnline", "socket", "Lio/socket/client/Socket;", "typingEvent", "getTypingEvent", "connect", "", "disconnect", "isConnected", "", "joinChat", "chatId", "leaveChat", "markMessageAsRead", "messageId", "sendMessage", "content", "contentType", "sendStopTyping", "sendTyping", "app_debug"})
public final class SocketManager {
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String SERVER_URL = "http://192.168.1.4:3000";
    @kotlin.jvm.Volatile
    @org.jetbrains.annotations.Nullable
    private static volatile io.socket.client.Socket socket;
    @org.jetbrains.annotations.NotNull
    private static final com.google.gson.Gson gson = null;
    @org.jetbrains.annotations.NotNull
    private static final kotlinx.coroutines.flow.MutableSharedFlow<com.akshar.messaging.data.models.Message> _messageReceived = null;
    @org.jetbrains.annotations.NotNull
    private static final kotlinx.coroutines.flow.SharedFlow<com.akshar.messaging.data.models.Message> messageReceived = null;
    @org.jetbrains.annotations.NotNull
    private static final kotlinx.coroutines.flow.MutableSharedFlow<com.akshar.messaging.data.socket.TypingEvent> _typingEvent = null;
    @org.jetbrains.annotations.NotNull
    private static final kotlinx.coroutines.flow.SharedFlow<com.akshar.messaging.data.socket.TypingEvent> typingEvent = null;
    @org.jetbrains.annotations.NotNull
    private static final kotlinx.coroutines.flow.MutableSharedFlow<com.akshar.messaging.data.socket.ConnectionStatus> _connectionStatus = null;
    @org.jetbrains.annotations.NotNull
    private static final kotlinx.coroutines.flow.SharedFlow<com.akshar.messaging.data.socket.ConnectionStatus> connectionStatus = null;
    @org.jetbrains.annotations.NotNull
    private static final io.socket.emitter.Emitter.Listener onConnect = null;
    @org.jetbrains.annotations.NotNull
    private static final io.socket.emitter.Emitter.Listener onDisconnect = null;
    @org.jetbrains.annotations.NotNull
    private static final io.socket.emitter.Emitter.Listener onConnectError = null;
    @org.jetbrains.annotations.NotNull
    private static final io.socket.emitter.Emitter.Listener onMessageReceived = null;
    @org.jetbrains.annotations.NotNull
    private static final io.socket.emitter.Emitter.Listener onTyping = null;
    @org.jetbrains.annotations.NotNull
    private static final io.socket.emitter.Emitter.Listener onStopTyping = null;
    @org.jetbrains.annotations.NotNull
    private static final io.socket.emitter.Emitter.Listener onUserOnline = null;
    @org.jetbrains.annotations.NotNull
    private static final io.socket.emitter.Emitter.Listener onUserOffline = null;
    @org.jetbrains.annotations.NotNull
    public static final com.akshar.messaging.data.socket.SocketManager INSTANCE = null;
    
    private SocketManager() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.SharedFlow<com.akshar.messaging.data.models.Message> getMessageReceived() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.SharedFlow<com.akshar.messaging.data.socket.TypingEvent> getTypingEvent() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.SharedFlow<com.akshar.messaging.data.socket.ConnectionStatus> getConnectionStatus() {
        return null;
    }
    
    @kotlin.jvm.Synchronized
    public final synchronized void connect() {
    }
    
    @kotlin.jvm.Synchronized
    public final synchronized void disconnect() {
    }
    
    public final void joinChat(@org.jetbrains.annotations.NotNull
    java.lang.String chatId) {
    }
    
    public final void leaveChat(@org.jetbrains.annotations.NotNull
    java.lang.String chatId) {
    }
    
    public final void sendMessage(@org.jetbrains.annotations.NotNull
    java.lang.String chatId, @org.jetbrains.annotations.NotNull
    java.lang.String content, @org.jetbrains.annotations.NotNull
    java.lang.String contentType) {
    }
    
    public final void sendTyping(@org.jetbrains.annotations.NotNull
    java.lang.String chatId) {
    }
    
    public final void sendStopTyping(@org.jetbrains.annotations.NotNull
    java.lang.String chatId) {
    }
    
    public final void markMessageAsRead(@org.jetbrains.annotations.NotNull
    java.lang.String messageId) {
    }
    
    public final boolean isConnected() {
        return false;
    }
}