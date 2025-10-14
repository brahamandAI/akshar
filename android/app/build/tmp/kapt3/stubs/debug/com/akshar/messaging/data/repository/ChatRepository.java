package com.akshar.messaging.data.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0005\u001a\u00020\u0006J.\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\rH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0006\u0010\u0010\u001a\u00020\u0006J*\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\u00120\b2\u0006\u0010\u0014\u001a\u00020\u000bH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0015\u0010\u0016J\"\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\u00120\bH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u000e\u0010\u001a\u001a\u00020\u00062\u0006\u0010\u0014\u001a\u00020\u000bJ\u000e\u0010\u001b\u001a\u00020\u00062\u0006\u0010\u0014\u001a\u00020\u000bJ\u000e\u0010\u001c\u001a\u00020\u00062\u0006\u0010\u001d\u001a\u00020\u000bJ\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020 0\u001fJ\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00130\u001fJ\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020#0\u001fJ6\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00130\b2\u0006\u0010\u0014\u001a\u00020\u000b2\u0006\u0010%\u001a\u00020\u000b2\b\b\u0002\u0010&\u001a\u00020\u000bH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\'\u0010(J\u000e\u0010)\u001a\u00020\u00062\u0006\u0010\u0014\u001a\u00020\u000bJ\u000e\u0010*\u001a\u00020\u00062\u0006\u0010\u0014\u001a\u00020\u000bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006+"}, d2 = {"Lcom/akshar/messaging/data/repository/ChatRepository;", "", "()V", "apiService", "Lcom/akshar/messaging/data/api/ApiService;", "connectSocket", "", "createChat", "Lkotlin/Result;", "Lcom/akshar/messaging/data/models/Chat;", "participantId", "", "isGroupChat", "", "createChat-0E7RQCE", "(Ljava/lang/String;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "disconnectSocket", "getChatMessages", "", "Lcom/akshar/messaging/data/models/Message;", "chatId", "getChatMessages-gIAlu-s", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getUserChats", "getUserChats-IoAF18A", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "joinChat", "leaveChat", "markAsRead", "messageId", "observeConnectionStatus", "Lkotlinx/coroutines/flow/Flow;", "Lcom/akshar/messaging/data/socket/ConnectionStatus;", "observeMessages", "observeTyping", "Lcom/akshar/messaging/data/socket/TypingEvent;", "sendMessage", "content", "contentType", "sendMessage-BWLJW6A", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "sendTyping", "stopTyping", "app_debug"})
public final class ChatRepository {
    @org.jetbrains.annotations.NotNull
    private final com.akshar.messaging.data.api.ApiService apiService = null;
    
    public ChatRepository() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<com.akshar.messaging.data.models.Message> observeMessages() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<com.akshar.messaging.data.socket.TypingEvent> observeTyping() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<com.akshar.messaging.data.socket.ConnectionStatus> observeConnectionStatus() {
        return null;
    }
    
    public final void connectSocket() {
    }
    
    public final void disconnectSocket() {
    }
    
    public final void joinChat(@org.jetbrains.annotations.NotNull
    java.lang.String chatId) {
    }
    
    public final void leaveChat(@org.jetbrains.annotations.NotNull
    java.lang.String chatId) {
    }
    
    public final void sendTyping(@org.jetbrains.annotations.NotNull
    java.lang.String chatId) {
    }
    
    public final void stopTyping(@org.jetbrains.annotations.NotNull
    java.lang.String chatId) {
    }
    
    public final void markAsRead(@org.jetbrains.annotations.NotNull
    java.lang.String messageId) {
    }
}