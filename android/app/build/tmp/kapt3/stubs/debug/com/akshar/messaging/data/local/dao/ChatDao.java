package com.akshar.messaging.data.local.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\r\bg\u0018\u00002\u00020\u0001J\u001e\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u000e\u0010\t\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0016\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\rH\u00a7@\u00a2\u0006\u0002\u0010\u000eJ\u0016\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0010J\u0014\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u00130\u0012H\'J\u0014\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u00130\u0012H\'J\u0018\u0010\u0015\u001a\u0004\u0018\u00010\r2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0010J\u0016\u0010\u0016\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\rH\u00a7@\u00a2\u0006\u0002\u0010\u000eJ\u001c\u0010\u0017\u001a\u00020\u00032\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\r0\u0013H\u00a7@\u00a2\u0006\u0002\u0010\u0019J\u0016\u0010\u001a\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0010J\u001e\u0010\u001b\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u001c\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u001e\u0010\u001d\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u001e\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u0016\u0010\u001f\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\rH\u00a7@\u00a2\u0006\u0002\u0010\u000e\u00a8\u0006 "}, d2 = {"Lcom/akshar/messaging/data/local/dao/ChatDao;", "", "archiveChat", "", "chatId", "", "archived", "", "(Ljava/lang/String;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteAll", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteChat", "chat", "Lcom/akshar/messaging/data/local/entities/ChatEntity;", "(Lcom/akshar/messaging/data/local/entities/ChatEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteChatById", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllChats", "Lkotlinx/coroutines/flow/Flow;", "", "getArchivedChats", "getChatById", "insertChat", "insertChats", "chats", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "markAsRead", "muteChat", "muted", "pinChat", "pinned", "updateChat", "app_debug"})
@androidx.room.Dao
public abstract interface ChatDao {
    
    @androidx.room.Query(value = "SELECT * FROM chats WHERE isArchived = 0 ORDER BY isPinned DESC, lastMessageTime DESC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.akshar.messaging.data.local.entities.ChatEntity>> getAllChats();
    
    @androidx.room.Query(value = "SELECT * FROM chats WHERE id = :chatId")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getChatById(@org.jetbrains.annotations.NotNull
    java.lang.String chatId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.akshar.messaging.data.local.entities.ChatEntity> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM chats WHERE isArchived = 1 ORDER BY lastMessageTime DESC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.akshar.messaging.data.local.entities.ChatEntity>> getArchivedChats();
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insertChat(@org.jetbrains.annotations.NotNull
    com.akshar.messaging.data.local.entities.ChatEntity chat, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insertChats(@org.jetbrains.annotations.NotNull
    java.util.List<com.akshar.messaging.data.local.entities.ChatEntity> chats, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Update
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateChat(@org.jetbrains.annotations.NotNull
    com.akshar.messaging.data.local.entities.ChatEntity chat, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteChat(@org.jetbrains.annotations.NotNull
    com.akshar.messaging.data.local.entities.ChatEntity chat, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM chats WHERE id = :chatId")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteChatById(@org.jetbrains.annotations.NotNull
    java.lang.String chatId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE chats SET unreadCount = 0 WHERE id = :chatId")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object markAsRead(@org.jetbrains.annotations.NotNull
    java.lang.String chatId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE chats SET isPinned = :pinned WHERE id = :chatId")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object pinChat(@org.jetbrains.annotations.NotNull
    java.lang.String chatId, boolean pinned, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE chats SET isMuted = :muted WHERE id = :chatId")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object muteChat(@org.jetbrains.annotations.NotNull
    java.lang.String chatId, boolean muted, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE chats SET isArchived = :archived WHERE id = :chatId")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object archiveChat(@org.jetbrains.annotations.NotNull
    java.lang.String chatId, boolean archived, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM chats")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteAll(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}