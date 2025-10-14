package com.akshar.messaging.data.local.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\t\bg\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u0016\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\fJ\u0016\u0010\r\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\fJ\u0018\u0010\u000f\u001a\u0004\u0018\u00010\u00072\u0006\u0010\n\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\fJ\u001c\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00120\u00112\u0006\u0010\u000e\u001a\u00020\u000bH\'J\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00070\u0012H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0014\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u001c\u0010\u0015\u001a\u00020\u00032\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00070\u0012H\u00a7@\u00a2\u0006\u0002\u0010\u0017J\u0016\u0010\u0018\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\fJ\u0016\u0010\u0019\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\fJ\u0016\u0010\u001a\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\b\u00a8\u0006\u001b"}, d2 = {"Lcom/akshar/messaging/data/local/dao/MessageDao;", "", "deleteAll", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteMessage", "message", "Lcom/akshar/messaging/data/local/entities/MessageEntity;", "(Lcom/akshar/messaging/data/local/entities/MessageEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteMessageById", "messageId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteMessagesForChat", "chatId", "getMessageById", "getMessagesForChat", "Lkotlinx/coroutines/flow/Flow;", "", "getUnsyncedMessages", "insertMessage", "insertMessages", "messages", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "markAsSynced", "markChatMessagesAsRead", "updateMessage", "app_debug"})
@androidx.room.Dao
public abstract interface MessageDao {
    
    @androidx.room.Query(value = "SELECT * FROM messages WHERE chatId = :chatId ORDER BY createdAt ASC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.akshar.messaging.data.local.entities.MessageEntity>> getMessagesForChat(@org.jetbrains.annotations.NotNull
    java.lang.String chatId);
    
    @androidx.room.Query(value = "SELECT * FROM messages WHERE id = :messageId")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getMessageById(@org.jetbrains.annotations.NotNull
    java.lang.String messageId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.akshar.messaging.data.local.entities.MessageEntity> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM messages WHERE isSynced = 0")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getUnsyncedMessages(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<com.akshar.messaging.data.local.entities.MessageEntity>> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insertMessage(@org.jetbrains.annotations.NotNull
    com.akshar.messaging.data.local.entities.MessageEntity message, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insertMessages(@org.jetbrains.annotations.NotNull
    java.util.List<com.akshar.messaging.data.local.entities.MessageEntity> messages, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Update
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateMessage(@org.jetbrains.annotations.NotNull
    com.akshar.messaging.data.local.entities.MessageEntity message, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteMessage(@org.jetbrains.annotations.NotNull
    com.akshar.messaging.data.local.entities.MessageEntity message, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM messages WHERE id = :messageId")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteMessageById(@org.jetbrains.annotations.NotNull
    java.lang.String messageId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE messages SET isRead = 1 WHERE chatId = :chatId")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object markChatMessagesAsRead(@org.jetbrains.annotations.NotNull
    java.lang.String chatId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE messages SET isSynced = 1 WHERE id = :messageId")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object markAsSynced(@org.jetbrains.annotations.NotNull
    java.lang.String messageId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM messages WHERE chatId = :chatId")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteMessagesForChat(@org.jetbrains.annotations.NotNull
    java.lang.String chatId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM messages")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteAll(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}